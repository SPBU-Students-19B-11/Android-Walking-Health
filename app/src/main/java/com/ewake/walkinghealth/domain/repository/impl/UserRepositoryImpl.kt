package com.ewake.walkinghealth.domain.repository.impl

import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.request.RegisterRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.domain.repository.user.UserRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class UserRepositoryImpl @Inject constructor(private val api: Api) : UserRepository {

    override suspend fun login(login: String, password: String): BaseResponse<String> {
        //return api.login(LoginRequest(login = login, password = password))

        return BaseResponse(code = 200, message = "Вы успешно авторизированы", result = "TestToken")
    }

    override suspend fun register(
        login: String,
        password: String,
        fullname: String,
        doctorLogin: String?
    ): BaseResponse<String> {
        /*return api.register(
            RegisterRequest(
                login = login,
                password = password,
                fullname = fullname,
                doctorLogin = doctorLogin
            )
        )*/

        return BaseResponse(
            code = 200,
            message = "Вы успешно зарегистрировались",
            result = "TestToken"
        )
    }
}