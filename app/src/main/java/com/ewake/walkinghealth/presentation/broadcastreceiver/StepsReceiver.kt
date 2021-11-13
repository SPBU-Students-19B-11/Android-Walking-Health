package com.ewake.walkinghealth.presentation.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.data.service.StepCountingService.Companion.STEPS_KEY
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class StepsReceiver @Inject constructor(appDatabase: AppDatabase) : BroadcastReceiver() {

    private val activityDao = appDatabase.getUserActivityDao()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val steps = it.getIntExtra(STEPS_KEY, 0)
            val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            var model = runBlocking { activityDao.getItem(currentDate) }
            if (model != null) {
                model.stepsCount += steps
                runBlocking { activityDao.update(model!!) }
            } else {
                model = UserActivityEntity(date = currentDate, stepsCount = steps)
                runBlocking { activityDao.insert(model) }
            }

            Log.d(TAG, "Current steps: ${model.stepsCount}")
        }
    }

    companion object {
        private const val TAG = "StepsReceiver"
    }
}