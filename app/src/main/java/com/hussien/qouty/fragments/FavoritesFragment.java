package com.hussien.qouty.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hussien.qouty.AppExecutor;
import com.hussien.qouty.adapter.QuotesAdapter;
import com.hussien.qouty.data.Quote;
import com.hussien.qouty.data.QuotesRepository;
import com.hussien.qouty.databinding.FragmentFavoritesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return FragmentFavoritesBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentFavoritesBinding binding = FragmentFavoritesBinding.bind(view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.favoriteRecyclerView.setLayoutManager(linearLayoutManager);

        populateRecyclerItems(binding);
    }

    private void populateRecyclerItems(FragmentFavoritesBinding binding) {
        Runnable runnable = () -> {
            if (getActivity() == null) return;
            QuotesRepository quotesRepository = QuotesRepository.getRepo_instance(getActivity().getApplication());
            List<Quote> quoteList = quotesRepository.getFavoritesQuoteList();
            QuotesAdapter adapter = new QuotesAdapter(getActivity());
            adapter.setQuoteList(quoteList);
            getActivity().runOnUiThread(() -> binding.favoriteRecyclerView.setAdapter(adapter));
        };

        AppExecutor.getInstance().getDiskIo().execute(runnable);
    }
}