package com.ewake.walkinghealth.domain.repository.impl

import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.api.model.request.MedicalSendDataRequest
import com.ewake.walkinghealth.data.api.model.request.SendMessageRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.GetDoctorsResult
import com.ewake.walkinghealth.data.api.model.response.MedicalGetDataResult
import com.ewake.walkinghealth.data.api.model.response.MessagesResult
import com.ewake.walkinghealth.data.api.model.response.SendMessageResponse
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.data.local.room.entity.UserActivityData
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class MedicalRepositoryImpl @Inject constructor(private val api: Api) : MedicalRepository {
    override suspend fun getDoctors(): BaseResponse<List<GetDoctorsResult>> {
        return api.getDoctors()

        /*return BaseResponse(
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
        )*/
    }

    override suspend fun getMessages(login: String): BaseResponse<MessagesResult> {
        return api.getMessages(login)

        /*return BaseResponse(
            code = 200,
            result = listOf(
                MessagesResult("1", "Тестовое сообщение", 10000000),
                MessagesResult("2", "Тестовое сообщение 2", 10010000),
                MessagesResult("3", "Тестовое сообщение 3", 10200000),
                MessagesResult("4", "Тестовое сообщение 4", 16700000),
                MessagesResult("5", "Тестовое сообщение 5", 20000000),
                MessagesResult("6", "Тестовое сообщение 6", 80000000),
                MessagesResult("7", "Тестовое сообщение 7", 90000000),
                MessagesResult("8", "Тестовое сообщение 8", 100000000),
                MessagesResult("9", "Тестовое сообщение 9", 110000000),
                MessagesResult("10", "Тестовое сообщение 10", 120000000)
            )
        )*/
    }

    override suspend fun sendData(data: UserActivityEntity): BaseResponse<Any> {
        return api.sendMedicalData(data)
        // return BaseResponse(code = 200, message = "OK")
    }

    override suspend fun getData(login: String?, date: String): BaseResponse<UserActivityEntity> {
        return api.getMedicalData(login, date)

        // return BaseResponse(200, result = appDatabase.getUserActivityDao().getItem(date))
    }

    override suspend fun getDates(patient: String): BaseResponse<List<String>> {
        return api.getMedicalDates(patient)
       //return BaseResponse(200, result = appDatabase.getUserActivityDao().getAll().map { it.date })
    }

    override suspend fun sendMessage(message: SendMessageRequest): BaseResponse<SendMessageResponse> {
        return api.sendMessage(message)
    }
}