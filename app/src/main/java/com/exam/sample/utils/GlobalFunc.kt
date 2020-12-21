package com.exam.sample.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.multidex.BuildConfig
import com.exam.sample.App
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers

fun shareUrl(shareUrl : String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareUrl)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    shareIntent.flags = FLAG_ACTIVITY_NEW_TASK
    App.getApplication().startActivity(shareIntent)
}

fun ToastMsg(resId:Int) {
    ToastMsg(App.getApplication().getString(resId))
}

@SuppressLint("CheckResult")
fun ToastMsg(msg:String) {
    Maybe.just(0).observeOn(AndroidSchedulers.mainThread()).subscribe {
        Toast.makeText(App.getApplication(), msg, Toast.LENGTH_SHORT).show()
    }
}

fun LogDebug(msg:String, tag:String = Const.LOG_TAG) {
    if (BuildConfig.DEBUG) {
        Log.v(tag, msg)
    }
}

fun checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
