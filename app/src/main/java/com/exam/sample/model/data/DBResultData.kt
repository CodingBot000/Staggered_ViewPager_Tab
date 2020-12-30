package com.exam.sample.model.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Database ResultData
 */
data class DBResultData(val flag:String, val data:Any?, val result:Boolean)
