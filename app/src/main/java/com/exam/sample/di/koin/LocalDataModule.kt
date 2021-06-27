package com.exam.sample.di

import androidx.room.Room


import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSource
import com.exam.sample.model.datasource.local.favorite.FavoriteInfoLocalDataSourceImpl
import com.exam.sample.database.AppDatabase
import com.exam.sample.database.FavoriteDao
import com.exam.sample.utils.Const
import org.koin.core.module.Module
import org.koin.dsl.module


val localDataModule: Module = module {

    single<FavoriteInfoLocalDataSource> { FavoriteInfoLocalDataSourceImpl(get()) }
    single<FavoriteDao> { get<AppDatabase>().favoriteDao() }

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, Const.DB_NAME
        ).build()
    }
}

