package com.exam.sample.model.datasource.remote.trending

import com.exam.sample.api.ApiService
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Const
import io.reactivex.Single


class TrendingRemoteDataSourceImpl(private val apiService: ApiService) :
    TrendingRemoteDataSource {

    override fun requestTrendingData(offset: Int): Single<TrendingData> {
        return apiService.getTrendingDataRequest(Const.API_KEY, Const.LIMIT, offset)
    }
}