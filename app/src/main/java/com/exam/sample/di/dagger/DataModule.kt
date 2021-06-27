package com.exam.sample.di.dagger

import android.content.Context
import androidx.room.Room
import com.exam.sample.database.AppDatabase
import com.exam.sample.database.FavoriteDao
import com.exam.sample.utils.Const

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            Const.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(blogDatabase: AppDatabase): FavoriteDao {
        return blogDatabase.favoriteDao()
    }
}