package com.exam.sample.model.datasource.local.favorite

import androidx.lifecycle.LiveData
import com.exam.sample.model.data.FavoriteInfo
import io.reactivex.Completable


interface FavoriteInfoLocalDataSource {
    fun getInfo(userId : String) : LiveData<FavoriteInfo>
    fun getInfoAll() : LiveData<List<FavoriteInfo>>
    fun insertInfo(favoriteInfo: FavoriteInfo) : Completable
    fun removeInfo(favoriteInfo: FavoriteInfo) : Completable
    fun updateInfo(favoriteInfo: FavoriteInfo) : Completable
}