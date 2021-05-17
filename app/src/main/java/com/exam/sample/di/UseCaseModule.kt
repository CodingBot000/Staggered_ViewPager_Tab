package com.exam.sample.di

import com.exam.sample.domain.usecase.*

import org.koin.dsl.module


val useCaseModule = module {

    single<UseCaseGetDetailData> { UseCaseGetDetailData(get()) }
    factory<UseCaseGetTrendingData> { UseCaseGetTrendingData(get()) }
    single<UseCaseGetSearchData> { UseCaseGetSearchData(get()) }
    single<UseCaseGetGIFsByIds> { UseCaseGetGIFsByIds(get()) }

    single<UseCaseDbSelect> { UseCaseDbSelect(get()) }
    single<UseCaseDbSelectAll> { UseCaseDbSelectAll(get()) }
    single<UseCaseDbRemove> { UseCaseDbRemove(get()) }
    single<UseCaseDbInsert> { UseCaseDbInsert(get()) }
    single<UseCaseDbUpdate> { UseCaseDbUpdate(get()) }
}
