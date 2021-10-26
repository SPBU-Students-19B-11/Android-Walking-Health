package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@JsonClass(generateAdapter = true)
open class BaseResponse<T>(
    @Json(name = "code")
    var code: Int,
    @Json(name = "result")
    var result: T? = null,
    @Json(name = "message")
    var message: String = ""
)