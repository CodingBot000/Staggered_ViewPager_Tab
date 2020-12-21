package com.exam.sample.utils.extention

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.exam.sample.utils.Const

fun View.setCellSize(width:Float, height:Float): ViewGroup.LayoutParams? {
    val rlp =  layoutParams
    val ratio: Float =  height / width
    rlp.height = ((Const.SCREEN_WIDTH_HALF)  * ratio).toInt()
    return rlp
}

fun View.setImageSize(width:Int, height:Int): ViewGroup.LayoutParams? {
    val rlp =  layoutParams
    rlp.width = width
    rlp.height = height
    return rlp
}


fun ImageView.setRainBowBackgroundColorByPosition(position : Int) {
    if (position == 0)
        setBackgroundResource(Const.COLORS_RAINBOW[0])
    else
        setBackgroundResource(Const.COLORS_RAINBOW[position % Const.COLORS_RAINBOW.size])

}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ViewGroup.inflate(@LayoutRes resource: Int): View {
    return LayoutInflater.from(context).inflate(resource, this, false)
}

fun View.onClick(onClick: () -> Unit) {
    setOnClickListener({ onClick() })
}

