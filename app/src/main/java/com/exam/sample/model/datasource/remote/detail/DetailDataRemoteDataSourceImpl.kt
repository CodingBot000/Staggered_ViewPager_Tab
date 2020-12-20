package com.exam.sample.model.datasource.remote.detail

import com.exam.sample.api.ApiService
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Const
import io.reactivex.Single


class DetailDataRemoteDataSourceImpl(private val apiService: ApiService) :
    DetailDataRemoteDataSource {

    override fun requestDetailData(id: String): Single<TrendingData> {
        return apiService.getDetailData(Const.API_KEY, id)
    }
}