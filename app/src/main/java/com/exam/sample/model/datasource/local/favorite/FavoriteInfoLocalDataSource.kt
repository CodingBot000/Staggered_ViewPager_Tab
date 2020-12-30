package com.exam.sample.model.datasource.local.favorite

import androidx.lifecycle.LiveData
import com.exam.sample.model.data.FavoriteInfo
import io.reactivex.Completable
import io.reactivex.Single


interface FavoriteInfoLocalDataSource {
    fun getInfo(userId : String) : Single<FavoriteInfo>
    fun getInfoAll() : Single<List<FavoriteInfo>>
    fun insertInfo(favoriteInfo: FavoriteInfo) : Completable
    fun removeInfo(favoriteInfo: FavoriteInfo) : Completable
    fun updateInfo(favoriteInfo: FavoriteInfo) : Completable
}