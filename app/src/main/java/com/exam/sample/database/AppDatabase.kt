package com.exam.sample.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.exam.sample.model.data.FavoriteInfo

@Database(
    entities = [FavoriteInfo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}