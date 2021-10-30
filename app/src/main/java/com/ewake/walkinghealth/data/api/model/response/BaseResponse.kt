package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.HttpURLConnection

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

fun <T> BaseResponse<T>.isSuccess(): Boolean {
    return code == HttpURLConnection.HTTP_OK
}

inline fun <T> BaseResponse<T>.onSuccess(onSuccess: (T?) -> Unit): BaseResponse<T> {
    if (isSuccess()) onSuccess.invoke(result)
    return this
}

inline fun <T> BaseResponse<T>.onFailure(onFailure: (String) -> Unit): BaseResponse<T> {
    if (!isSuccess()) onFailure.invoke(message)
    return this
}