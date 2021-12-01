package com.ewake.walkinghealth.data.local.prefs

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class UserDataPrefs @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var login: String?
        get() = prefs.getString(LOGIN_KEY, null)
        @SuppressLint("ApplySharedPref")
        set(value) {
            prefs.edit().putString(LOGIN_KEY, value).apply()
        }

    var token: String?
        get() = prefs.getString(TOKEN_KEY, null)
        @SuppressLint("ApplySharedPref")
        set(value) {
            prefs.edit().putString(TOKEN_KEY, value).apply()
        }

    var isDoctor: Boolean
        get() = prefs.getBoolean(DOCTOR_KEY, false)
        set(value) {
            prefs.edit().putBoolean(DOCTOR_KEY, value).apply()
        }

    var stepSize: Float
        get() = prefs.getFloat(STEP_SIZE_KEY, 0.5f)
        set(value) {
            prefs.edit().putFloat(STEP_SIZE_KEY, value).apply()
        }

    companion object {
        private const val PREFS_NAME = "userDataPrefs"

        private const val LOGIN_KEY = "login"
        private const val TOKEN_KEY = "token"
        private const val DOCTOR_KEY = "isDoctor"
        private const val STEP_SIZE_KEY = "stepSize"
    }
}