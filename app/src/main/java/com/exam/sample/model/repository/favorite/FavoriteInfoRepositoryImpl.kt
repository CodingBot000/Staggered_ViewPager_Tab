package com.exam.sample.model.repository.favorite


import androidx.lifecycle.LiveData
import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSource
import com.exam.sample.model.datasource.remote.favorite.FavoriteInfoRemoteDataSource
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.data.FavoriteInfo
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteInfoRepositoryImpl(
    private val favoriteInfoRemoteDataSource: FavoriteInfoRemoteDataSource,
    private val favoriteInfoLocalDataSource: FavoriteInfoLocalDataSource
) : FavoriteInfoRepository
{

    override fun requestGIFsByIds(idsList: String): Single<TrendingData> {
        return favoriteInfoRemoteDataSource.requestGIFsByIds(idsList)
    }

    override fun getFavoriteAllDB(): Single<List<FavoriteInfo>> {
        return favoriteInfoLocalDataSource.getInfoAll()
    }

    override fun getFavoriteDB(userId: String): Single<FavoriteInfo> {
        return favoriteInfoLocalDataSource.getInfo(userId)
    }

    override fun insertFavoriteDB(favoriteInfo: FavoriteInfo): Single<Long> {
        return favoriteInfoLocalDataSource.insertInfo(favoriteInfo)
    }

    override fun removeFavoriteDB(favoriteInfo: FavoriteInfo): Single<Int> {
        return favoriteInfoLocalDataSource.removeInfo(favoriteInfo)
    }

    override fun updateFavoriteDB(favoriteInfo: FavoriteInfo): Single<Int> {
        return favoriteInfoLocalDataSource.updateInfo(favoriteInfo)
    }
}