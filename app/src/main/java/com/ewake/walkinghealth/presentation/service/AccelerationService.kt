package com.ewake.walkinghealth.presentation.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ewake.walkinghealth.presentation.ui.activity.MainActivity
import kotlin.math.pow
import kotlin.math.sqrt

class AccelerationService : Service(), SensorEventListener {

    private val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as? SensorManager }
    private val accelerationSensor by lazy { sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    private val notification: Notification by lazy {
        NotificationCompat.Builder(this, MainActivity.SERVICE_NOTIFICATION_CHANNEL)
            .setContentTitle("Вычисляем ваше ускорение")
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }

    private var accelerationSum = 0.0
    private var accelerationCount = 0L

    override fun onCreate() {
        super.onCreate()
        Log.i(ACCELERATION_SERVICE_TAG, "Starting")

        val result = sensorManager?.registerListener(this, accelerationSensor, 0)
        if (result != true) {
            stopSelf()
        }

        startForeground(SERVICE_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACCELERATION_SERVICE_TAG, "Stopping")
        sensorManager?.unregisterListener(this, accelerationSensor)
        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val acceleration = sqrt((x.pow(2) + y.pow(2) + z.pow(2))) - GRAVITY

            if (acceleration > ACCURACY && acceleration < MAX_ACCELERATION) notifyAcceleration(acceleration)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun notifyAcceleration(acceleration: Double) {
        accelerationSum += acceleration
        ++accelerationCount

        if (accelerationCount > DElAY_COUNT) {
            val intent =
                Intent(ACCELERATION_SERVICE_TAG).putExtra(ACCELERATION_KEY, accelerationSum / accelerationCount)
            sendBroadcast(intent)
            accelerationCount = 0
            accelerationSum = 0.0
        }
    }

    companion object {
        const val ACCELERATION_KEY = "acceleration"

        const val ACCELERATION_SERVICE_TAG = "AccelerationService"

        private const val GRAVITY = 9.80665
        private const val ACCURACY = 1
        private const val MAX_ACCELERATION = 30

        private const val DElAY_COUNT = 1000

        private const val SERVICE_ID = 101
    }
}