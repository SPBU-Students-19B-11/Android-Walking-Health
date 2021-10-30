package com.ewake.walkinghealth.domain.usecase

import com.ewake.walkinghealth.domain.repository.user.UserRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class SaveUserTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun invoke(token: String) = userRepository.saveToken(token)
}