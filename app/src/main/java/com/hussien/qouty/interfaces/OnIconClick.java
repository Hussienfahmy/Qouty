package com.hussien.qouty.interfaces;

import android.view.View;

import com.hussien.qouty.databinding.RowItemQuoteBinding;

public interface OnIconClick {
    void OnClick(View view, int position, RowItemQuoteBinding binding);
}