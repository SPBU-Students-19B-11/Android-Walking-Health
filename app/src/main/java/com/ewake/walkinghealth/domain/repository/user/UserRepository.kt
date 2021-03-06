package com.ewake.walkinghealth.domain.repository.user

import com.ewake.walkinghealth.data.api.model.response.AuthResponse
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.UserDataResult
import com.ewake.walkinghealth.domain.repository.base.BaseRepository

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
interface UserRepository : BaseRepository {
    suspend fun login(login: String, password: String): BaseResponse<AuthResponse>

    suspend fun register(
        login: String,
        password: String,
        fullname: String,
        doctorLogin: String? = null,
        stepLength: Double?
    ): BaseResponse<AuthResponse>

    suspend fun getUserData(login: String? = null): BaseResponse<UserDataResult>

    suspend fun saveToken(token: String)
    suspend fun saveLogin(login: String)

    fun getToken(): String?
    fun getLogin(): String?
}