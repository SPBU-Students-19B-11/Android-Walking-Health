package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageResponse(
    @Json(name = "login")
    var login: String = "",
    @Json(name = "message")
    var message: String = "",
    @Json(name = "timestamp")
    var timestamp: Long = 0
)
