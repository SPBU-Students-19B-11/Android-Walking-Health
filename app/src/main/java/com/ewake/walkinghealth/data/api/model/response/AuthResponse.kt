package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "token")
    var token: String = "",
    @Json(name = "login")
    var login: String = "",
    @Json(name = "isDoctor")
    var isDoctor: Boolean = false
)