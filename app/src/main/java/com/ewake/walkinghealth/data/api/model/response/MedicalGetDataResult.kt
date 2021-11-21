package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MedicalGetDataResult(
    @Json(name = "date")
    var date: String = "",
    @Json(name = "steps")
    var steps: Int = 0,
    @Json(name = "speed")
    var speed: Double = 0.0,
    @Json(name = "acceleration")
    var acceleration: Double = 0.0,
    @Json(name = "distance")
    var distance: Double = 0.0
)