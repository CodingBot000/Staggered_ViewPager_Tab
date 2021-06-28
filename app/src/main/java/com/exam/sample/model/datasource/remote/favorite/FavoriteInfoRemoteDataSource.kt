package com.exam.sample.model.datasource.remote.favorite

import com.exam.sample.model.data.TrendingData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single

interface FavoriteInfoRemoteDataSource {
    fun requestGIFsByIds(idsList : String) : Single<TrendingData>
}