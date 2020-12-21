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



fun <T> Context.startActivityDetailExtras(clazz: Class<T>, it: TrendingDetail) {
    val intent = Intent(this, clazz)
    intent.putExtra(
        Const.EXTRA_KEY_INTERACTION,
        InteractionData(it.id, it.type, it.username, it.title, it.embed_url,
            it.images.downsized.url, it.images.fixed_width_small.url)
    )
    startActivity(intent)

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

