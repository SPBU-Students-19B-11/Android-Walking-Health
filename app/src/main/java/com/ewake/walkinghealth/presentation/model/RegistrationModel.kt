package com.ewake.walkinghealth.presentation.model

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
data class RegistrationModel(
    var login: String = "",
    var password: String = "",
    var fullname: String = "",
    var isDoctor: Boolean = false,
    var selectedDoctor: SimpleUserModel? = null
)