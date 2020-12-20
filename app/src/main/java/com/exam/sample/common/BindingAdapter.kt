package com.exam.sample.common


import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.exam.sample.App
import com.exam.sample.R
import com.exam.sample.utils.Const
import java.util.*


@BindingAdapter("photo")
fun setImage(view: ImageView, url: String){
    val index = Random().nextInt(Const.COLORS_RAINBOW.size - 1)
    Glide.with(view)
        .load(url)
        .apply(
            RequestOptions.placeholderOf(Const.COLORS_RAINBOW[index])
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}
