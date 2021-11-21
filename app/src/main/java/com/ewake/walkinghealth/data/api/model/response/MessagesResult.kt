package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@JsonClass(generateAdapter = true)
data class MessagesResult(
    @Json(name = "message")
    var message: String = "",
    @Json(name = "timestamp")
    var timestamp: Long = 0
)