package com.hussien.qouty.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hussien.qouty.R;
import com.hussien.qouty.data.Quote;
import com.hussien.qouty.databinding.RowItemQuoteBinding;
import com.hussien.qouty.interfaces.OnIconClick;
import com.hussien.qouty.utilities.ActionsUtils;
import com.hussien.qouty.utilities.SharedPreferenceUtils;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
    private static final String TAG = "QuotesAdapter";
    private List<Quote> quoteList;
    private final Context context;

    public QuotesAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void setQuoteList(List<Quote> quoteList) {
        if (this.quoteList != null){
            int size = this.quoteList.size();
            this.quoteList.clear();
            notifyItemRangeRemoved(0, size);
            this.quoteList.addAll(quoteList);
        }else {
            this.quoteList = quoteList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemQuoteBinding binding = RowItemQuoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        OnIconClick onIconClick = (view, position, binding1) -> {
            int clicked_id = view.getId();
            if (clicked_id == R.id.image_text_share){
                performOnClickTextShare(context, quoteList.get(position));
            }else if (clicked_id == R.id.image_love) {
                performOnClickLove(position, binding1);
            }else if (clicked_id == R.id.image_image_share) {
                performOnClickImageShare(position);
            }else if (clicked_id == R.id.image_copy) {
                performOnClickCopy(context, quoteList.get(position));
            }
        };

        return new ViewHolder(binding, onIconClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote quote = quoteList.get(position);
        RowItemQuoteBinding binding = RowItemQuoteBinding.bind(holder.itemView);

        generateTags(quote.getTags(), binding.tagContainer);
        String quoteContent = '\u201D'+ quote.getText() +'\u201C';
        binding.quoteLayout.quoteText.setText(quoteContent);
        binding.quoteLayout.quoteAuthor.setText(quote.getAuthor());
        binding.quoteLayout.quoteText.setTypeface(SharedPreferenceUtils.getUserChoiceFontFamily(context));
        binding.quoteLayout.quoteAuthor.setTypeface(SharedPreferenceUtils.getUserChoiceFontFamily(context));

        setLovedColor(binding, quote);
    }

    private void setLovedColor(RowItemQuoteBinding binding, Quote quote) {
        int color;

        if (quote.isLoved()){
            color = ContextCompat.getColor(context, R.color.red);
        }else {
            color = Color.WHITE;
        }
        ImageViewCompat.setImageTintList(binding.imageLove, ColorStateList.valueOf(color));
    }

    private void updateAnimatedLovedColor(RowItemQuoteBinding binding, Quote quote) {
        int color_from;
        int color_to;

        if (quote.isLoved()){
            color_from = Color.WHITE;
            color_to = ContextCompat.getColor(context, R.color.red);
        }else {
            color_from = ContextCompat.getColor(context, R.color.red);
            color_to = Color.WHITE;
        }

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), color_from, color_to);
        colorAnimation.setDuration(300); // milliseconds
        colorAnimation.addUpdateListener(animator ->
                binding.imageLove.setColorFilter((int) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    private void generateTags(String tag, LinearLayout tagContainer) {
        tagContainer.removeAllViews();
        TextView tag_textView = new TextView(tagContainer.getContext());
        tag_textView.setText(tag);
        tag_textView.setTextColor(ContextCompat.getColor(context, R.color.pink));
        tagContainer.addView(tag_textView);
    }

    @Override
    public int getItemCount() {
        return quoteList == null ? 0 : quoteList.size();
    }

    @Override
    public long getItemId(int position) {
        return quoteList.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return quoteList.get(position).getId();
    }

    private void performOnClickLove(int position, RowItemQuoteBinding binding){
        Quote quote = quoteList.get(position);
        ActionsUtils.updateQuoteLoved(context,quote);
        updateAnimatedLovedColor(binding, quote);
    }

    public static void performOnClickTextShare(Context context, Quote quote){
        ActionsUtils.shareText(context, quote);
    }

    public static void performOnClickCopy(Context context, Quote quote) {
        ActionsUtils.copyText(context, quote);
    }

    private void performOnClickImageShare(int position) {
        ActionsUtils.shareImg(context, quoteList.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull RowItemQuoteBinding binding, OnIconClick onIconClick) {
            super(binding.getRoot());
            View.OnClickListener onClickListener = v -> onIconClick.OnClick(v, getBindingAdapterPosition(), binding);

            binding.imageTextShare.setOnClickListener(onClickListener);
            binding.imageLove.setOnClickListener(onClickListener);
            binding.imageCopy.setOnClickListener(onClickListener);
            binding.imageImageShare.setOnClickListener(onClickListener);

        }
    }
}
