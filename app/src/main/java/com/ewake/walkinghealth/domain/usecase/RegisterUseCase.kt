package com.ewake.walkinghealth.domain.usecase

import com.ewake.walkinghealth.domain.repository.user.UserRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class RegisterUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        login: String,
        password: String,
        fullname: String,
        doctorLogin: String? = null,
        stepLength: Double? = null
    ) = userRepository.register(login, password, fullname, doctorLogin, stepLength)
}