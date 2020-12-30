package com.exam.sample.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.exam.sample.model.data.FavoriteInfo
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface FavoriteDao {
    @Query("SELECT * FROM TABLE_FAVORITE")
    fun getAll(): Single<List<FavoriteInfo>>

    @Query("SELECT * FROM TABLE_FAVORITE where userId = :userId")
    fun getData(userId : String) : Single<FavoriteInfo>

    @Update
    fun update(favoriteInfo : FavoriteInfo) : Completable

    @Update
    fun update(list :List<FavoriteInfo>) : Completable

    @Delete
    fun delete(favoriteInfo : FavoriteInfo) : Completable

    @Delete
    fun delete(list :List<FavoriteInfo>) : Completable

    @Insert(onConflict = REPLACE)
    fun insert(favoriteInfo: FavoriteInfo) : Completable


}
