package com.ewake.walkinghealth.domain.usecase

import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import javax.inject.Inject

class MedicalGetDataUseCase @Inject constructor(private val medicalRepository: MedicalRepository) {
    suspend operator fun invoke(login: String?, date: String) = medicalRepository.getData(login, date)
}