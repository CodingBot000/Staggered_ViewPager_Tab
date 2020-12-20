package com.exam.sample.model.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * api data class
 */
data class TrendingData(@SerializedName("data") val trendingItems:ArrayList<TrendingDetail>,
                        val pagination: PaginationData, val meta:MetaData
)
data class TrendingDetail(val type:String, val id:String,  val username:String,
                         val title:String, val embed_url:String,
                          val images: ImagesData
)
data class ImagesData(val original: OriginalData, val downsized: DownSizedData,
                  val fixed_width_small: FixedWidthSmallData
)
data class OriginalData(val height:String, val width:String, val url:String)
data class DownSizedData(val height:String, val width:String, val size:String, val url:String )
data class FixedWidthSmallData(val height:String?, val width:String?, val url:String)
data class PaginationData(val total_count:Int, val count:Int, val offset:Int);
data class MetaData(val status:Int, val msg:String, val response_id:String);
