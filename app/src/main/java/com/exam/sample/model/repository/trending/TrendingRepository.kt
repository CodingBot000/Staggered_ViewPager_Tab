package com.exam.sample.model.repository.trending

import com.exam.sample.model.data.TrendingData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import io.reactivex.Single

interface TrendingRepository {
    fun requestTrendingData(offset: Int, rating:String): Single<TrendingData>
}