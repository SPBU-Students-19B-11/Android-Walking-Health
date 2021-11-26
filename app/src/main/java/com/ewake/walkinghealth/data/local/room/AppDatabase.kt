package com.ewake.walkinghealth.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ewake.walkinghealth.data.local.room.dao.UserActivityDao
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.data.local.room.typeconverter.ActivityDataTypeConverter

@Database(version = 1, entities = [UserActivityEntity::class])
@TypeConverters(value = [ActivityDataTypeConverter::class])
abstract class AppDatabase: RoomDatabase() {
   abstract fun getUserActivityDao(): UserActivityDao
}