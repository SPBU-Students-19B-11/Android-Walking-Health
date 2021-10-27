package com.ewake.walkinghealth.domain.usecase

import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class DoctorsUseCase @Inject constructor(private val medicalRepository: MedicalRepository) {
    suspend operator fun invoke() = medicalRepository.getDoctors()
}