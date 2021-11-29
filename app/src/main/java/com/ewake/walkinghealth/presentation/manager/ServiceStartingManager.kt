package com.ewake.walkinghealth.presentation.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ewake.walkinghealth.data.local.prefs.BroadcastPrefs
import com.ewake.walkinghealth.presentation.broadcastreceiver.UserActivityReceiver
import com.ewake.walkinghealth.presentation.service.UserActivityService
import javax.inject.Inject

class ServiceStartingManager @Inject constructor(
    private val userActivityReceiver: UserActivityReceiver,
    private val broadcastPrefs: BroadcastPrefs
) {

    fun startServices(activity: Activity) {
        activity.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(this)
            } else {
                startDefault(this)
            }

            registerReceivers(this)
        }
    }

    fun sendData(context: Context) {
        val workRequest = OneTimeWorkRequest.Builder(SendingActivityWorker::class.java).build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForeground(activity: Activity) {
        activity.apply {
            startForegroundService(Intent(baseContext, UserActivityService::class.java))
        }
    }

    private fun startDefault(activity: Activity) {
        activity.apply {
            startService(Intent(baseContext, UserActivityService::class.java))
        }
    }

    private fun registerReceivers(activity: Activity) {
        activity.apply {
            if (!broadcastPrefs.isReceiverStarted)
                registerReceiver(userActivityReceiver, IntentFilter(UserActivityService.USER_ACTIVITY_SERVICE_TAG))
            broadcastPrefs.isReceiverStarted = true
        }
    }
}