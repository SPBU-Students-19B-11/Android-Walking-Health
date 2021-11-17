package com.ewake.walkinghealth.data.api.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageRequest(
    @Json(name = "message")
    var message: String,
    @Json(name = "PatientLogin")
    var patientLogin: String
)