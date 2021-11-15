package com.ewake.walkinghealth.presentation.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.presentation.service.AccelerationService
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AccelerationReceiver @Inject constructor(appDatabase: AppDatabase) : BroadcastReceiver() {
    private val activityDao = appDatabase.getUserActivityDao()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val acceleration = it.getDoubleExtra(AccelerationService.ACCELERATION_KEY, 0.0)
            val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            var model = runBlocking { activityDao.getItem(currentDate) }
            if (model != null) {
                model.accelerationSum += acceleration
                ++model.accelerationCount
                runBlocking { activityDao.update(model!!) }
            } else {
                model = UserActivityEntity(date = currentDate, accelerationSum = acceleration, accelerationCount = 1)
                runBlocking { activityDao.insert(model) }
            }

            Log.d(TAG, "Current acceleration: ${model.accelerationSum / model.accelerationCount}")
        }
    }

    companion object {
        private const val TAG = "AccelerationReceiver"
    }
}