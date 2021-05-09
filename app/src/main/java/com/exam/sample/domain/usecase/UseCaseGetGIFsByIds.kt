package com.exam.sample.domain.usecase


import com.exam.sample.domain.usecase.base.SingleUseCase
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.model.repository.trending.TrendingRepository

import io.reactivex.Single


class UseCaseGetSearchData (private val searchRepository: SearchRepository)
: SingleUseCase<TrendingData>() {

    private var offset: Int = 0
    private lateinit var keyword: String

    fun setData(keyword: String, offset: Int) {
        this.offset = offset
        this.keyword = keyword
    }

    override fun buildUseCaseSingle(): Single<TrendingData> {
        return searchRepository.requestSearchData(keyword, offset)
    }
}