package com.hussien.quoty.ui.quotes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hussien.quoty.R
import com.hussien.quoty.databinding.FragmentQoutesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class QuotesFragment : Fragment() {

    private val viewModel: QuotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentQoutesBinding =
            FragmentQoutesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.recyclerView.adapter = QuotesAdapter(OnQuoteActionClickListener { action, quoteId ->
            viewModel.onQuoteAction(action, quoteId)
        })

        viewModel.typeFace.observe(viewLifecycleOwner) {
            val adapter = binding.recyclerView.adapter as QuotesAdapter
            adapter.setTypeFace(it)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        lifecycleScope.launchWhenStarted {
            val onlyFavourites = viewModel.showOnlyFavourites.first()
            menu.findItem(R.id.menu_favorites).run {
                isChecked = onlyFavourites
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorites -> viewModel.onFavouritesClick()
            R.id.menu_setting -> findNavController().navigate(
                QuotesFragmentDirections.actionQuotesToMenuSetting()
            )
        }
        return true
    }
}