package com.ewake.walkinghealth.domain.repository.impl

import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.request.RegisterRequest
import com.ewake.walkinghealth.data.api.model.response.BaseResponse
import com.ewake.walkinghealth.data.api.model.response.UserDataResult
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.domain.repository.user.UserRepository
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class UserRepositoryImpl @Inject constructor(
    private val api: Api,
    private val userDataPrefs: UserDataPrefs
) : UserRepository {

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

    override suspend fun getUserData(): BaseResponse<UserDataResult> {
        // return api.getUserData()

        return BaseResponse(
            200,
            result = UserDataResult(
                "test",
                "Иванов Иван Иванович",
                false,
                "testtesttest"
            )
        )
    }

    override suspend fun saveToken(token: String) {
        userDataPrefs.token = token
    }

    override suspend fun saveLogin(login: String) {
        userDataPrefs.login = login
    }

    override suspend fun getToken(): String? = userDataPrefs.token

    override suspend fun getLogin(): String? = userDataPrefs.login
}