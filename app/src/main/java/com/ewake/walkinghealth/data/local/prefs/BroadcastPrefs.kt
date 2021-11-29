package com.ewake.walkinghealth.data.local.prefs

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BroadcastPrefs @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences(BROADCAST_PREFS_NAME, Context.MODE_PRIVATE)

    var isReceiverStarted: Boolean
        get() = prefs.getBoolean(IS_STARTED_TAG, false)
        set(value) {
            prefs.edit().putBoolean(IS_STARTED_TAG, value).apply()
        }

    companion object {
        private const val BROADCAST_PREFS_NAME = "BroadcastPrefs"

        private const val IS_STARTED_TAG = "isStarted"
    }
}