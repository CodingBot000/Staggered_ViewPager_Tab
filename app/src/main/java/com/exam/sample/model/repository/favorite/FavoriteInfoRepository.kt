package com.exam.sample.model.repository.favorite

import androidx.lifecycle.LiveData
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData
import io.reactivex.Completable

import io.reactivex.Single

interface FavoriteInfoRepository {
    fun requestGIFsByIds(idsList : String): Single<TrendingData>

    fun getFavoriteAllDB() : LiveData<List<FavoriteInfo>>
    fun getFavoriteDB(userId : String) : LiveData<FavoriteInfo>
    fun insertFavoriteDB(favoriteInfo : FavoriteInfo) : Completable
    fun removeFavoriteDB(favoriteInfo : FavoriteInfo) : Completable
    fun updateFavoriteDB(favoriteInfo : FavoriteInfo) : Completable
}