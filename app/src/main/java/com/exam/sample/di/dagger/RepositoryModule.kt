package com.exam.sample.di.dagger


import com.exam.sample.api.ApiService
import com.exam.sample.database.FavoriteDao
import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSource
import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSourceImpl
import com.exam.sample.model.datasource.remote.detail.DetailDataRemoteDataSource
import com.exam.sample.model.datasource.remote.detail.DetailDataRemoteDataSourceImpl
import com.exam.sample.model.datasource.remote.favorite.FavoriteInfoRemoteDataSource
import com.exam.sample.model.datasource.remote.favorite.FavoriteInfoRemoteDataSourceImpl
import com.exam.sample.model.datasource.remote.search.SearchRemoteDataSource
import com.exam.sample.model.datasource.remote.search.SearchRemoteDataSourceImpl
import com.exam.sample.model.datasource.remote.trending.TrendingRemoteDataSource
import com.exam.sample.model.datasource.remote.trending.TrendingRemoteDataSourceImpl
import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.repository.detail.DetailDataRepositoryImpl
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository
import com.exam.sample.model.repository.favorite.FavoriteInfoRepositoryImpl
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.model.repository.search.SearchRepositoryImpl
import com.exam.sample.model.repository.trending.TrendingRepository
import com.exam.sample.model.repository.trending.TrendingRepositoryImpl
import com.exam.sample.utils.Const
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTrendingRemoteDataSource(
        apiService: ApiService
    ): TrendingRemoteDataSource {
        return TrendingRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideTrendingRepository(
        trendingRemoteDataSource: TrendingRemoteDataSource
    ): TrendingRepository {
        return TrendingRepositoryImpl(trendingRemoteDataSource)
    }


    @Singleton
    @Provides
    fun provideDetailDataRepository(
        detailDataRemoteDataSource: DetailDataRemoteDataSource,
        favoriteInfoLocalDataSource: FavoriteInfoLocalDataSource
    ): DetailDataRepository {
        return DetailDataRepositoryImpl(detailDataRemoteDataSource, favoriteInfoLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideDetailDataRemoteDataSource(
        apiService: ApiService
    ): DetailDataRemoteDataSource {
        return DetailDataRemoteDataSourceImpl(apiService)
    }



    @Singleton
    @Provides
    fun provideFavoriteInfoRepository(
        favoriteInfoRemoteDataSource: FavoriteInfoRemoteDataSource,
        favoriteInfoLocalDataSource: FavoriteInfoLocalDataSource
    ): FavoriteInfoRepository {
        return FavoriteInfoRepositoryImpl(favoriteInfoRemoteDataSource, favoriteInfoLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideFavoriteInfoRemoteDataSource (
        apiService: ApiService
    ): FavoriteInfoRemoteDataSource {
        return FavoriteInfoRemoteDataSourceImpl(apiService)
    }


    @Singleton
    @Provides
    fun provideFavoriteInfoLocalDataSource(
        favoriteDao: FavoriteDao
    ): FavoriteInfoLocalDataSource {
        return FavoriteInfoLocalDataSourceImpl(favoriteDao)
    }



    @Singleton
    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchRemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(searchRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(
        apiService: ApiService
    ) : SearchRemoteDataSource {
        return SearchRemoteDataSourceImpl(apiService)
    }

}

