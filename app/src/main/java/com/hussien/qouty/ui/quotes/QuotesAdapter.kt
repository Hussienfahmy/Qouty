package com.hussien.qouty.ui.quotes

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hussien.qouty.databinding.RowItemQuoteBinding
import com.hussien.qouty.ext.addToParentOf
import com.hussien.qouty.ext.duplicate
import com.hussien.qouty.ext.scaleWithFading
import com.hussien.qouty.models.Quote
import com.hussien.qouty.models.QuotesDiffUtil
import com.hussien.qouty.ui.quotes.OnQuoteActionClickListener.QuoteAction

class QuotesAdapter(
    private val quoteActionClickListener: OnQuoteActionClickListener,
    private var typeface: Typeface? = null
) :
    ListAdapter<Quote, QuotesAdapter.ViewHolder>(QuotesDiffUtil()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            currentList[position],
            typeface,
            quoteActionClickListener
        )
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTypeFace(typeface: Typeface?) {
        this.typeface = typeface
        // i don't think there any other way except this
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(
        private val binding: RowItemQuoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quote: Quote, typeface: Typeface?, quoteActionClickListener: OnQuoteActionClickListener) {
            binding.quote = quote
            binding.clickListener = quoteActionClickListener
            binding.typeFace = typeface

            binding.imageLove.setOnClickListener { imageView ->
                // as the listener only supply the id and not the view
                // i have to handle the animation logic here
                if (!quote.isLoved) {
                    // create a copy and animate with fading
                    imageView.duplicate().run {
                        addToParentOf(imageView).scaleWithFading(2f, 0f)
                    }
                }
                // forward to the outer listener to save in the database
                quoteActionClickListener.onClick(QuoteAction.LOVE, quote.id)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                RowItemQuoteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}