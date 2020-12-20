package com.exam.sample.model.datasource.local.favorite

import androidx.lifecycle.LiveData
import com.exam.sample.database.FavoriteDao
import com.exam.sample.model.data.FavoriteInfo
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteInfoLocalDataSourceImpl(private val favoriteDao: FavoriteDao) :
    FavoriteInfoLocalDataSource {

    override fun getInfo(userId : String) : LiveData<FavoriteInfo> {
        return favoriteDao.getData(userId)
    }

    override fun getInfoAll() : LiveData<List<FavoriteInfo>> {
        return favoriteDao.getAll()
    }

    override fun insertInfo(favoriteInfo: FavoriteInfo) : Completable {
        return favoriteDao.insert(favoriteInfo)
    }

    override fun removeInfo(favoriteInfo: FavoriteInfo) : Completable {
        return favoriteDao.delete(favoriteInfo)
    }

    override fun updateInfo(favoriteInfo: FavoriteInfo) : Completable {
        return favoriteDao.update(favoriteInfo)
    }
}