package com.exam.sample.model.usecase

import android.annotation.SuppressLint
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSource
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCaseDbManager (private val favoriteInfoRepository: FavoriteInfoRepository) {

    fun getFavoriteAll() : Single<List<FavoriteInfo>> {
        return favoriteInfoRepository.getFavoriteAllDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFavorite(userId: String): Single<FavoriteInfo> {
        return favoriteInfoRepository.getFavoriteDB(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removeFavorite(favoriteInfo: FavoriteInfo) : Completable {
        return favoriteInfoRepository.removeFavoriteDB(favoriteInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertFavorites(favoriteInfo: FavoriteInfo) : Completable  {
        return favoriteInfoRepository.insertFavoriteDB(favoriteInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateFavorite(favoriteInfo: FavoriteInfo): Completable {
        return favoriteInfoRepository.updateFavoriteDB(favoriteInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}