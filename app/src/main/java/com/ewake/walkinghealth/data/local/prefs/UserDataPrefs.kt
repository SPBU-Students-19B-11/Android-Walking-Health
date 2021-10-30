package com.ewake.walkinghealth.data.local.prefs

import android.annotation.SuppressLint
import android.content.Context
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class UserDataPrefs @Inject constructor(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var login: String?
        get() = prefs.getString(LOGIN_KEY, null)
        @SuppressLint("ApplySharedPref")
        set(value) {
            prefs.edit().putString(LOGIN_KEY, value).commit()
        }

    var token: String?
        get() = prefs.getString(TOKEN_KEY, null)
        @SuppressLint("ApplySharedPref")
        set(value) {
            prefs.edit().putString(TOKEN_KEY, value).commit()
        }

    companion object {
        private const val PREFS_NAME = "userDataPrefs"

        private const val LOGIN_KEY = "login"
        private const val TOKEN_KEY = "token"
    }
}