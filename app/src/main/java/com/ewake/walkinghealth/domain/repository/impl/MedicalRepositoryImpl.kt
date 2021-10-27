package com.ewake.walkinghealth.domain.repository.impl

import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.GetDoctorsResult
import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class MedicalRepositoryImpl @Inject constructor(private val api: Api) : MedicalRepository {
    override suspend fun getDoctors(): BaseResponse<List<GetDoctorsResult>> {
        // return api.getDoctors()

        return BaseResponse(
            200,
            result = listOf(
                GetDoctorsResult("123", "Петров Петр Иванович"),
                GetDoctorsResult("1234", "Иванов Иван Иванович"),
                GetDoctorsResult("1235", "Иванов Петр Петрович"),
                GetDoctorsResult("1233", "Иванов Иван Петрович"),
                GetDoctorsResult("1232", "Тест Петр Иванович"),
                GetDoctorsResult("1231", "Петров Тест Иванович"),
                GetDoctorsResult("1230", "Петров Петр Тест")
            )
        )
    }
}