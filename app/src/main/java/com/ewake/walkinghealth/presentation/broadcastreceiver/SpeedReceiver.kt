package com.ewake.walkinghealth.presentation.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.presentation.service.SpeedService.Companion.SPEED_KEY
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class SpeedReceiver @Inject constructor(appDatabase: AppDatabase) : BroadcastReceiver() {

    private val activityDao = appDatabase.getUserActivityDao()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val speed = it.getFloatExtra(SPEED_KEY, 0f)
            val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            var model = runBlocking { activityDao.getItem(currentDate) }
            if (model != null) {
                model.speedSum += speed
                ++model.speedCount
                runBlocking { activityDao.update(model!!) }
            } else {
                model = UserActivityEntity(date = currentDate, speedSum = speed.toDouble(), speedCount = 1)
                runBlocking { activityDao.insert(model) }
            }

            Log.d(TAG, "Current speed: ${model.speedSum / model.speedCount}")
        }
    }

    companion object {
        private const val TAG = "SpeedReceiver"
    }
}