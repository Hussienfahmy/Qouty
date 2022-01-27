package com.hussien.quoty.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("list")
fun <T> RecyclerView.bindData(
    list: List<T>?
) {
    adapter.doIfTypeIs<ListAdapter<T, *>> {
        submitList(list)
    }
}