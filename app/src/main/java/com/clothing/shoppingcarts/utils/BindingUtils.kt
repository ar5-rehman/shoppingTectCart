package com.clothing.shoppingcarts.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.clothing.shoppingcarts.R

// DataBinding for ImageView to load image url
@BindingAdapter("imgUrl")
fun ImageView.loadImgUrl(imgUrl: String?) {
    Glide.with(this)
        .load(imgUrl)
        .centerCrop()
        .placeholder(R.drawable.women_icon)
        .into(this)
}