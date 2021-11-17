package com.ewake.walkinghealth.data.api

import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.request.MedicalSendDataRequest
import com.ewake.walkinghealth.data.api.model.request.RegisterRequest
import com.ewake.walkinghealth.data.api.model.request.SendMessageRequest
import com.ewake.walkinghealth.data.api.model.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */

interface Api {
    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<AuthResponse>

    @GET("medical/getDoctors")
    suspend fun getDoctors(): BaseResponse<List<GetDoctorsResult>>

    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<AuthResponse>

    @GET("user/getData")
    suspend fun getUserData(@Query("login") login: String? = null): BaseResponse<UserDataResult>

    @GET("medical/getMessages")
    suspend fun getMessages(@Query("PatientLogin") login: String): BaseResponse<List<MessagesResult>>

    @POST("medical/sendMessage")
    suspend fun sendMessage(@Body request: SendMessageRequest): BaseResponse<SendMessageResponse>

    @POST("medical/sendData")
    suspend fun sendMedicalData(@Body request: MedicalSendDataRequest): BaseResponse<Any>
}