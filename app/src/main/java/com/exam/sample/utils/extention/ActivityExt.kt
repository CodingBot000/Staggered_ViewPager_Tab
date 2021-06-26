package com.exam.sample.utils.extention

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.content.ContextCompat
import com.exam.sample.R
import com.exam.sample.model.data.InteractionData
import com.exam.sample.model.data.TrendingDetail
import com.exam.sample.utils.Const
import com.exam.sample.utils.checkIsMaterialVersion

import kotlin.math.max



fun <T> Activity.startActivityDetailExtras(clazz: Class<T>, it: TrendingDetail) {
    val intent = Intent(this, clazz)
    intent.putExtra(
        Const.EXTRA_KEY_INTERACTION,
        InteractionData(it.id, it.type, it.username, it.title, it.embed_url,
            it.images.downsized.url, it.images.fixed_width_small.url)
    )
    startActivity(intent)
    openScaleTranslate()

}


fun Activity.fadeIn() {
    overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
}

fun Activity.slideUp() {
    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
}

fun Activity.openScaleTranslate() {
    overridePendingTransition(R.anim.open_scale,R.anim.close_translate);
}

fun Activity.closeScaleTranslate() {
    overridePendingTransition(R.anim.close_scale,R.anim.open_translate);
}

fun Activity.slideOutToRight() {
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
}


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

