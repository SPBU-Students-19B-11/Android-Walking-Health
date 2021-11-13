package com.ewake.walkinghealth.domain.usecase

import com.ewake.walkinghealth.domain.repository.user.UserRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class GetUserLoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getLogin()
}