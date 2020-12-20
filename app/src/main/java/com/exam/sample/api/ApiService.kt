package com.exam.sample.api


import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Const
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/trending")
    fun getTrendingDataRequest(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<TrendingData>

    @GET("gifs/trending")
    fun getDetailData(
        @Query("api_key") apiKey: String,
        @Query("ids") ids: String
    ): Single<TrendingData>

    @GET("gifs/{gif_id}")
    fun getGIFByID(
        @Path("gif_id") gif_id : String,
        @Query("api_key") apiKey: String
    ): Single<TrendingData>

    @GET("gifs")
    fun getGIFsByIDs(
        @Query("api_key") apiKey: String,
        @Query("ids") ids: String
    ): Single<TrendingData>

    @GET("gifs/search")
    fun getSearchFromKeyword(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("q") q: String
    ): Single<TrendingData>
}