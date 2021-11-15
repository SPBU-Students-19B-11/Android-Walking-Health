package com.ewake.walkinghealth.presentation.service

import android.Manifest
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.ewake.walkinghealth.presentation.ui.activity.MainActivity

class SpeedService: Service() {

    private val locationManager by lazy { getSystemService(LOCATION_SERVICE) as? LocationManager }

    private val locationListener = LocationListener {
        if (it.hasSpeed() && it.speed > 0) {
            val speed = it.speed * 3600 / 1000
            val intent = Intent(SPEED_SERVICE_TAG).putExtra(SPEED_KEY, speed)
            sendBroadcast(intent)
        }
    }

    private val notification: Notification by lazy {
        NotificationCompat.Builder(this, MainActivity.SERVICE_NOTIFICATION_CHANNEL)
            .setContentTitle("Вычисляем вашу скорость")
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(SPEED_SERVICE_TAG, "Starting")

        startForeground(SERVICE_ID, notification)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, locationListener)
        } else {
            stopSelf()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(SPEED_SERVICE_TAG, "Stopping")
        stopForeground(true)
    }

    companion object {
        const val SPEED_SERVICE_TAG = "SpeedService"

        const val SPEED_KEY = "speed"

        private const val SERVICE_ID = 102
    }
}