package com.ewake.walkinghealth.domain.repository.impl

import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.api.model.request.LoginRequest
import com.ewake.walkinghealth.data.api.model.request.RegisterRequest
import com.ewake.walkinghealth.data.api.model.response.AuthResponse
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

    override suspend fun login(login: String, password: String): BaseResponse<AuthResponse> {
        //return api.login(LoginRequest(login = login, password = password))

        return BaseResponse(
            code = 200, message = "Вы успешно авторизированы", result = AuthResponse(
                token = "TestToken",
                login = login,
                isDoctor = true
            )
        )
    }

    override suspend fun register(
        login: String,
        password: String,
        fullname: String,
        doctorLogin: String?
    ): BaseResponse<AuthResponse> {
        /*return api.register(
            RegisterRequest(
                login = login,
                password = password,
                fullname = fullname,
                doctorLogin = doctorLogin
            )
        )*/

        return BaseResponse(
            code = 200, message = "Вы успешно зарегистрировались", result = AuthResponse(
                token = "TestToken",
                login = login,
                isDoctor = true
            )
        )
    }

    override suspend fun getUserData(login: String?): BaseResponse<UserDataResult> {
        // return api.getUserData(login)

        return BaseResponse(
            200,
            result = UserDataResult(
                "test",
                "Иванов Иван Иванович",
                true,
                "testtesttest",
                patients = listOf(
                    UserDataResult.Patients("test", "Иванов Иван Иванович"),
                    UserDataResult.Patients("test", "Иванов Иван Иванович"),
                    UserDataResult.Patients("test", "Иванов Иван Иванович"),
                    UserDataResult.Patients("test", "Иванов Иван Иванович")
                )
            )
        )
    }

    override suspend fun saveToken(token: String) {
        userDataPrefs.token = token
    }

    override suspend fun saveLogin(login: String) {
        userDataPrefs.login = login
    }

    override fun getToken(): String? = userDataPrefs.token

    override fun getLogin(): String? = userDataPrefs.login
}