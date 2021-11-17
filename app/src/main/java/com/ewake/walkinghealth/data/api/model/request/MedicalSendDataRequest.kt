package com.ewake.walkinghealth.data.api.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MedicalSendDataRequest(
    @Json(name = "date")
    var date: String,
    @Json(name = "steps")
    var steps: Int,
    @Json(name = "speed")
    var speed: Double,
    @Json(name = "acceleration")
    var acceleration: Double,
    @Json(name = "distance")
    var distance: Double
)
