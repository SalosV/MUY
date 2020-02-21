package com.example.muy.utils

import android.view.View
import android.view.View.GONE
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {

    visibility = visible?.let {
        if (visible) View.VISIBLE else GONE
    } ?: GONE
}