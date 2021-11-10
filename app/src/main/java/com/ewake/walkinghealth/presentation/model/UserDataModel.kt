package com.ewake.walkinghealth.presentation.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
data class UserDataModel(
    override var login: String = "",
    override var fullname: String = "",
    var isDoctor: Boolean = false,
    var doctor: SimpleUserModel? = null,
    var patients: List<SimpleUserModel>? = null
): SimpleUserModel()