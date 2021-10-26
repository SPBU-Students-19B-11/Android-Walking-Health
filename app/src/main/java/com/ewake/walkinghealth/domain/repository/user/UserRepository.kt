package com.ewake.walkinghealth.domain.repository.user

import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.domain.repository.base.BaseRepository

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
interface UserRepository : BaseRepository {
   suspend fun login(login: String, password: String): BaseResponse<String>
}