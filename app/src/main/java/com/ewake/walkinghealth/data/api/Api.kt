package com.ewake.walkinghealth.data.api

import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.request.RegisterRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.GetDoctorsResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
}