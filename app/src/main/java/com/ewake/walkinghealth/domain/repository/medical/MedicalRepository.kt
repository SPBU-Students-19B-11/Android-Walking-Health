package com.ewake.walkinghealth.domain.repository.medical

import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.GetDoctorsResult
import com.ewake.walkinghealth.data.api.model.response.MessagesResult
import com.ewake.walkinghealth.domain.repository.base.BaseRepository

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
interface MedicalRepository : BaseRepository {
    suspend fun getDoctors(): BaseResponse<List<GetDoctorsResult>>
    suspend fun getMessages(login: String): BaseResponse<List<MessagesResult>>
}