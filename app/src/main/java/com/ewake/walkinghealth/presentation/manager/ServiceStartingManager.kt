package com.ewake.walkinghealth.presentation.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ewake.walkinghealth.presentation.broadcastreceiver.AccelerationReceiver
import com.ewake.walkinghealth.presentation.broadcastreceiver.SpeedReceiver
import com.ewake.walkinghealth.presentation.broadcastreceiver.StepsReceiver
import com.ewake.walkinghealth.presentation.service.AccelerationService
import com.ewake.walkinghealth.presentation.service.SpeedService
import com.ewake.walkinghealth.presentation.service.StepCountingService
import javax.inject.Inject

class ServiceStartingManager @Inject constructor(
    private val stepsReceiver: StepsReceiver,
    private val accelerationReceiver: AccelerationReceiver,
    private val speedReceiver: SpeedReceiver,
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
            startForegroundService(Intent(baseContext, StepCountingService::class.java))
            startForegroundService(Intent(baseContext, AccelerationService::class.java))
            startForegroundService(Intent(baseContext, SpeedService::class.java))
        }
    }

    private fun startDefault(activity: Activity) {
        activity.apply {
            startService(Intent(baseContext, StepCountingService::class.java))
            startService(Intent(baseContext, AccelerationService::class.java))
            startService(Intent(baseContext, SpeedService::class.java))
        }
    }

    private fun registerReceivers(activity: Activity) {
        activity.apply {
            registerReceiver(stepsReceiver, IntentFilter(StepCountingService.STEP_COUNTING_SERVICE_TAG))
            registerReceiver(accelerationReceiver, IntentFilter(AccelerationService.ACCELERATION_SERVICE_TAG))
            registerReceiver(speedReceiver, IntentFilter(SpeedService.SPEED_SERVICE_TAG))
        }
    }
}