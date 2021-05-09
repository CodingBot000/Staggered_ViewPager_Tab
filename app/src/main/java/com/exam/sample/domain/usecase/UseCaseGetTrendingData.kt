package com.exam.sample.domain.usecase


import com.exam.sample.domain.usecase.base.SingleUseCase
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.repository.trending.TrendingRepository

import io.reactivex.Single


class UseCaseGetTrendingData (private val trendingRepository: TrendingRepository)
: SingleUseCase<TrendingData>() {

    private var offset: Int = 0
    private lateinit var rating: String

    fun setData(offset: Int, rating: String) {
        this.offset = offset
        this.rating = rating
    }

    override fun buildUseCaseSingle(): Single<TrendingData> {
        return trendingRepository.requestTrendingData(offset, rating)
    }
}