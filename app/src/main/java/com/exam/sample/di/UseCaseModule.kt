package com.exam.sample.di

import com.exam.sample.domain.usecase.*

import org.koin.dsl.module


val useCaseModule = module {
    single<UseCaseDbManager> { UseCaseDbManager(get()) }
    single<UseCaseGetDetailData> { UseCaseGetDetailData(get()) }
    factory<UseCaseGetTrendingData> { UseCaseGetTrendingData(get()) }
    single<UseCaseGetSearchData> { UseCaseGetSearchData(get()) }
    single<UseCaseGetGIFsByIds> { UseCaseGetGIFsByIds(get()) }

}
