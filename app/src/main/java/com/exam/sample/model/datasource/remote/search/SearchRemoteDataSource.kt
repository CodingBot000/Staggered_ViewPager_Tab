package com.exam.sample.model.datasource.remote.search

import com.exam.sample.model.data.TrendingData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single

interface SearchRemoteDataSource {
    fun requestSearchData(keyword: String, offset: Int): Single<TrendingData>
}