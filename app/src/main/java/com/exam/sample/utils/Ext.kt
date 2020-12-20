package com.exam.sample.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.exam.sample.R
import com.exam.sample.model.data.InteractionData
import com.exam.sample.model.data.TrendingDetail
import kotlin.math.max

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

fun <T> Context.startActivityDetailExtras(clazz: Class<T>, it: TrendingDetail) {
    val intent = Intent(this, clazz)
    intent.putExtra(Const.EXTRA_KEY_INTERACTION,
        InteractionData(it.id, it.type, it.username, it.title, it.embed_url,
            it.images.downsized.url, it.images.fixed_width_small.url)
    )
    startActivity(intent)

}


fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

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

fun checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun Activity.circularRevealedAtCenter(view: View) {
    val cx = (view.left + view.right) / 2
    val cy = (view.top + view.bottom) / 2
    val finalRadius = max(view.width, view.height)

    if (checkIsMaterialVersion() && view.isAttachedToWindow) {
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
        view.visibility = View.VISIBLE
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue))
        anim.duration = 550
        anim.start()
    }
}


