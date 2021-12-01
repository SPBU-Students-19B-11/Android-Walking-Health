package com.ewake.walkinghealth.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class UserActivityEntity(
    @PrimaryKey
    @Json(name = "date")
    var date: String,
    @Json(name = "data")
    var data: MutableList<UserActivityData> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class UserActivityData(
    @Json(name = "timestamp")
    var timestamp: Long = 0,
    @Json(name = "speed")
    var speed: Double = 0.0,
    @Json(name = "acceleration")
    var acceleration: Double = 0.0,
    @Json(name = "distance")
    var distance: Double = 0.0
)