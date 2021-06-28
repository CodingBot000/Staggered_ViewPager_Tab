package com.exam.sample.model.repository.detail

import androidx.lifecycle.LiveData
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Completable

import io.reactivex.Single

interface DetailDataRepository {
    fun requestDetailData(id: String): Single<TrendingData>

    fun getFavoriteDB(userId : String) : Single<FavoriteInfo>
    fun insertFavoriteDB(favoriteInfo : FavoriteInfo) : Single<Long>
    fun removeFavoriteDB(favoriteInfo : FavoriteInfo) : Single<Int>
    fun updateFavoriteDB(favoriteInfo : FavoriteInfo) : Single<Int>
}