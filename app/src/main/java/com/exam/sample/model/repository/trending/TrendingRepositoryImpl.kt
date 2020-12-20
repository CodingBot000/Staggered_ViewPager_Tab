package com.exam.sample.model.repository.trending


import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.datasource.remote.trending.TrendingRemoteDataSource
import io.reactivex.Single

class TrendingRepositoryImpl(
    private val trendingRemoteDataSource: TrendingRemoteDataSource
) : TrendingRepository
{
    override fun requestTrendingData(offset: Int): Single<TrendingData> {
        return trendingRemoteDataSource.requestTrendingData(offset)
    }
}