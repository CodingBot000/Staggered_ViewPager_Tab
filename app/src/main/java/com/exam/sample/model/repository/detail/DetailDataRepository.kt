package com.exam.sample.model.repository.detail

import androidx.lifecycle.LiveData
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData
import io.reactivex.Completable

import io.reactivex.Single

interface DetailDataRepository {
    fun requestDetailData(id: String): Single<TrendingData>

    fun getFavoriteDB(userId : String) : LiveData<FavoriteInfo>
    fun insertFavoriteDB(favoriteInfo : FavoriteInfo) : Completable
    fun removeFavoriteDB(favoriteInfo : FavoriteInfo) : Completable
    fun updateFavoriteDB(favoriteInfo : FavoriteInfo) : Completable
}