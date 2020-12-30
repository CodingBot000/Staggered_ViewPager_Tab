package com.exam.sample.model.repository.detail


import androidx.lifecycle.LiveData
import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSource
import com.exam.sample.model.datasource.remote.detail.DetailDataRemoteDataSource
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.data.FavoriteInfo
import io.reactivex.Completable
import io.reactivex.Single

class DetailDataRepositoryImpl(
    private val detailDataRemoteDataSource: DetailDataRemoteDataSource,
    private val favoriteInfoLocalDataSource: FavoriteInfoLocalDataSource
) : DetailDataRepository
{
    override fun requestDetailData(id: String): Single<TrendingData> {
        return detailDataRemoteDataSource.requestDetailData(id)
    }

    override fun getFavoriteDB(userId: String): Single<FavoriteInfo> {
        return favoriteInfoLocalDataSource.getInfo(userId)
    }

    override fun insertFavoriteDB(favoriteInfo: FavoriteInfo): Completable {
        return favoriteInfoLocalDataSource.insertInfo(favoriteInfo)
    }

    override fun removeFavoriteDB(favoriteInfo: FavoriteInfo): Completable {
        return favoriteInfoLocalDataSource.removeInfo(favoriteInfo)
    }

    override fun updateFavoriteDB(favoriteInfo: FavoriteInfo): Completable {
        return favoriteInfoLocalDataSource.updateInfo(favoriteInfo)
    }

}