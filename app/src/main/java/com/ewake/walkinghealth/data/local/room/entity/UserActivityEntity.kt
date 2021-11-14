package com.ewake.walkinghealth.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserActivityEntity(
    @PrimaryKey
    var date: String,
    var stepsCount: Int = 0,
    var accelerationSum: Double = 0.0,
    var accelerationCount: Long = 0
)