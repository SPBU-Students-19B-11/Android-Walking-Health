package com.ewake.walkinghealth.data.api.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "login")
    var login: String,
    @Json(name = "password")
    var password: String
)
