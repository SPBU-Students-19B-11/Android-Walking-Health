package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "AuthToken")
    var token: String = "",
    @Json(name = "isDoctor")
    var isDoctor: Boolean = false,
    @Json(name = "stepLength")
    var stepLength: Float? = null
)