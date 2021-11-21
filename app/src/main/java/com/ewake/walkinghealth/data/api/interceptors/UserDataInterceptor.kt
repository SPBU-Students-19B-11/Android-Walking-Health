package com.ewake.walkinghealth.data.api.interceptors

import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class UserDataInterceptor @Inject constructor(private val prefs: UserDataPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        prefs.login?.let { builder.addHeader(USER_LOGIN_HEADER, it) }
        prefs.token?.let { builder.addHeader(TOKEN_HEADER, it) }

        return chain.proceed(builder.build())
    }

    companion object {
        private const val TOKEN_HEADER = "AuthToken"
        private const val USER_LOGIN_HEADER = "CurrentUserLogin"
    }
}