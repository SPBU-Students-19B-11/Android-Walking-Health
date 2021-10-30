package com.ewake.walkinghealth.data.api

import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.request.RegisterRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.GetDoctorsResult
import com.ewake.walkinghealth.data.api.model.response.MessagesResult
import com.ewake.walkinghealth.data.api.model.response.UserDataResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */

interface Api {
    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<String>

    @GET("medical/getDoctors")
    suspend fun getDoctors(): BaseResponse<List<GetDoctorsResult>>

    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @GET("user/getData")
    suspend fun getUserData(): BaseResponse<UserDataResult>

    @GET("medical/getMessages")
    suspend fun getMessages(@Query("userId") login: String): BaseResponse<List<MessagesResult>>
}