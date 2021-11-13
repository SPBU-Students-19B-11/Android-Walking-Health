package com.ewake.walkinghealth.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity

@Dao
interface UserActivityDao {
    @Query("SELECT * FROM UserActivityEntity")
    suspend fun getAll(): List<UserActivityEntity>

    @Query("SELECT * FROM UserActivityEntity WHERE date LIKE :date LIMIT 1")
    suspend fun getItem(date: String): UserActivityEntity?

    @Query("DELETE FROm UserActivityEntity WHERE date LIKE :date")
    suspend fun delete(date: String)

    @Update
    suspend fun update(userActivityEntity: UserActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userActivityEntity: UserActivityEntity)
}