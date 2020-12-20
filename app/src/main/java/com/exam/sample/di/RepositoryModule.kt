package com.exam.sample.di

import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.repository.detail.DetailDataRepositoryImpl
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository
import com.exam.sample.model.repository.favorite.FavoriteInfoRepositoryImpl
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.model.repository.search.SearchRepositoryImpl
import com.exam.sample.model.repository.trending.TrendingRepository
import com.exam.sample.model.repository.trending.TrendingRepositoryImpl
import org.koin.dsl.module


val repoModule = module {
    single<TrendingRepository> { TrendingRepositoryImpl(get()) }
    single<DetailDataRepository> { DetailDataRepositoryImpl(get(), get()) }
    single<FavoriteInfoRepository> { FavoriteInfoRepositoryImpl(get(), get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}
