package com.ewake.walkinghealth.domain.repository.medical

import com.ewake.walkinghealth.data.api.model.request.MedicalSendDataRequest
import com.ewake.walkinghealth.data.api.model.request.SendMessageRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.GetDoctorsResult
import com.ewake.walkinghealth.data.api.model.response.MedicalGetDataResult
import com.ewake.walkinghealth.data.api.model.response.MessagesResult
import com.ewake.walkinghealth.data.api.model.response.SendMessageResponse
import com.ewake.walkinghealth.domain.repository.base.BaseRepository

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
interface MedicalRepository : BaseRepository {
    suspend fun getDoctors(): BaseResponse<List<GetDoctorsResult>>
    suspend fun getMessages(login: String): BaseResponse<List<MessagesResult>>
    suspend fun sendData(data: MedicalSendDataRequest): BaseResponse<Any>
    suspend fun getData(login: String?): BaseResponse<List<MedicalGetDataResult>>
    suspend fun sendMessage(message: SendMessageRequest): BaseResponse<SendMessageResponse>
}