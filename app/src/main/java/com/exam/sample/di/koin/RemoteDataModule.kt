package com.exam.sample.di

import com.exam.sample.model.datasource.remote.detail.DetailDataRemoteDataSource
import com.exam.sample.model.datasource.remote.detail.DetailDataRemoteDataSourceImpl
import com.exam.sample.model.datasource.remote.favorite.FavoriteInfoRemoteDataSourceImpl
import com.exam.sample.model.datasource.remote.favorite.FavoriteInfoRemoteDataSource
import com.exam.sample.model.datasource.remote.search.SearchRemoteDataSource
import com.exam.sample.model.datasource.remote.search.SearchRemoteDataSourceImpl
import com.exam.sample.model.datasource.remote.trending.TrendingRemoteDataSource
import com.exam.sample.model.datasource.remote.trending.TrendingRemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module


val remoteDataModule: Module = module {
    single<TrendingRemoteDataSource> { TrendingRemoteDataSourceImpl(get()) }
    single<DetailDataRemoteDataSource> { DetailDataRemoteDataSourceImpl(get()) }
    single<FavoriteInfoRemoteDataSource> { FavoriteInfoRemoteDataSourceImpl(get()) }
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(get()) }
}
