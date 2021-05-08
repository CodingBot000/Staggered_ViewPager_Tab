package com.exam.sample.di

import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.repository.detail.DetailDataRepositoryImpl
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository
import com.exam.sample.model.repository.favorite.FavoriteInfoRepositoryImpl
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.model.repository.search.SearchRepositoryImpl
import com.exam.sample.model.repository.trending.TrendingRepository
import com.exam.sample.model.repository.trending.TrendingRepositoryImpl
import com.exam.sample.model.usecase.UseCaseApiManager
import com.exam.sample.model.usecase.UseCaseDbManager
import org.koin.dsl.module


val useCaseModule = module {
    single<UseCaseDbManager> { UseCaseDbManager(get()) }
    single<UseCaseApiManager> { UseCaseApiManager(get(), get(), get(), get()) }
}
