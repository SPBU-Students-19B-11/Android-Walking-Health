package com.ewake.walkinghealth.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@JsonClass(generateAdapter = true)
data class UserDataResult(
    @Json(name = "login")
    var login: String = "",
    @Json(name = "fullname")
    var fullname: String = "",
    @Json(name = "isDoctor")
    var isDoctor: Boolean = false,
    @Json(name = "doctorId")
    var doctorId: String? = null,
    @Json(name = "patients")
    var patients: List<Patients>? = null
) {
    @JsonClass(generateAdapter = true)
    data class Patients(
        @Json(name = "login")
        var login: String = "",
        @Json(name = "fullname")
        var fullname: String = ""
    )
}