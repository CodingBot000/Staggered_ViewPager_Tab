package com.exam.sample.domain.usecase


import com.exam.sample.domain.usecase.base.SingleUseCase
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.detail.DetailDataRepository

import io.reactivex.Single


class UseCaseGetDetailData (private val detailDataRepository: DetailDataRepository)
: SingleUseCase<TrendingData>() {

    private lateinit var dataId: String

    fun setDataId(id: String) {
        dataId = id
    }

    override fun buildUseCaseSingle(): Single<TrendingData> {
        return detailDataRepository.requestDetailData(dataId)
    }
}