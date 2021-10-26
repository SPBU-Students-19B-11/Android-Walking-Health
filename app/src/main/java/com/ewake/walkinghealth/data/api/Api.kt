package com.ewake.walkinghealth.data.api

import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */

interface Api {
    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<String>
}