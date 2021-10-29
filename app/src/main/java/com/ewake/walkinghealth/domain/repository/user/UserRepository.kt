package com.ewake.walkinghealth.domain.repository.user

import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.UserDataResult
import com.ewake.walkinghealth.domain.repository.base.BaseRepository

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
interface UserRepository : BaseRepository {
    suspend fun login(login: String, password: String): BaseResponse<String>
    suspend fun register(
        login: String,
        password: String,
        fullname: String,
        doctorLogin: String? = null
    ): BaseResponse<String>
    suspend fun getUserData(): BaseResponse<UserDataResult>
}