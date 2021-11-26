package com.ewake.walkinghealth.presentation.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.data.local.room.entity.UserActivityData
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.presentation.service.UserActivityService
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UserActivityReceiver @Inject constructor(appDatabase: AppDatabase) : BroadcastReceiver() {

    private val dao = appDatabase.getUserActivityDao()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val steps = it.getIntExtra(UserActivityService.STEPS_KEY, 0)
            val speed = it.getDoubleExtra(UserActivityService.SPEED_KEY, 0.0)
            val acceleration = it.getDoubleExtra(UserActivityService.ACCELERATION_KEY, 0.0)
            val timestamp = it.getLongExtra(UserActivityService.TIMESTAMP_KEY, 0L)

            val data = UserActivityData(
                timestamp = timestamp,
                speed = speed,
                acceleration = acceleration,
                distance = steps * 0.5
            )

            val currentDate = SimpleDateFormat("dd.MM.yyy", Locale.getDefault()).format(Date())

            var item = runBlocking { dao.getItem(currentDate) }

            if (item == null) {
                item = UserActivityEntity(date = currentDate, data = mutableListOf(data))
                runBlocking { dao.insert(item) }
            } else {
                item.data.add(data)
                runBlocking { dao.update(item) }
            }

        }
    }

    companion object {
        private const val TAG = "UserActivityReceiver"
    }
}