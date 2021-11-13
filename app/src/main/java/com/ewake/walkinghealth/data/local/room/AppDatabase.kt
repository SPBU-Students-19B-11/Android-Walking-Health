package com.ewake.walkinghealth.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ewake.walkinghealth.data.local.room.dao.UserActivityDao
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity

@Database(version = 1, entities = [UserActivityEntity::class])
abstract class AppDatabase: RoomDatabase() {
   abstract fun getUserActivityDao(): UserActivityDao
}