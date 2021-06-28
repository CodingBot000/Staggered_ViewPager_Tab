package com.exam.sample.model.repository.search

import com.exam.sample.model.data.TrendingData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import io.reactivex.Single


interface SearchRepository {
    fun requestSearchData(keyword: String, offset: Int): Single<TrendingData>
}