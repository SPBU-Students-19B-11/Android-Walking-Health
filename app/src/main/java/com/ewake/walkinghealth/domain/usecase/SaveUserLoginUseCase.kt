package com.ewake.walkinghealth.domain.usecase

import com.ewake.walkinghealth.domain.repository.user.UserRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class SaveUserLoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun invoke(login: String) = userRepository.saveLogin(login)
}