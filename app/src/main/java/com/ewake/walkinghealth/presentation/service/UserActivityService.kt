package com.ewake.walkinghealth.presentation.service

import android.Manifest
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.ewake.walkinghealth.presentation.ui.activity.MainActivity
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.pow
import kotlin.math.sqrt

class UserActivityService : Service(), SensorEventListener, LocationListener {

    private val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as? SensorManager }

    private val accelerationSensor by lazy { sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    private val stepSensor by lazy { sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) }

    private val locationManager by lazy { getSystemService(LOCATION_SERVICE) as? LocationManager }

    private var accelerationSum = 0.0
    private var accelerationCount = 0

    private var speedSum = 0.0
    private var speedCount = 0

    private var steps = 0

    private val timer = Timer()

    private val notification: Notification by lazy {
        NotificationCompat.Builder(this, MainActivity.SERVICE_NOTIFICATION_CHANNEL)
            .setContentTitle("Записываем данные активности")
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(USER_ACTIVITY_SERVICE_TAG, "Starting")

        val registerResult = sensorManager?.registerListener(this, accelerationSensor, 0) == true
            && sensorManager?.registerListener(this, stepSensor, 0) == true

        if (!registerResult) {
            stopSelf()
            return
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        } else {
            stopSelf()
            return
        }

        timer.schedule(TIMER_TIME, TIMER_TIME) {
            Log.d(USER_ACTIVITY_SERVICE_TAG, accelerationCount.toString())
            Log.d(USER_ACTIVITY_SERVICE_TAG, speedCount.toString())

            if (steps > 0) {
                sendData()
            }

            accelerationSum = 0.0
            accelerationCount = 0

            speedSum = 0.0
            speedCount = 0

            steps = 0
        }
        startForeground(SERVICE_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(USER_ACTIVITY_SERVICE_TAG, "Stopping")

        sensorManager?.apply {
            unregisterListener(this@UserActivityService, accelerationSensor)
            unregisterListener(this@UserActivityService, stepSensor)
        }

        stopForeground(true)
    }

    private fun sendData() {
        val timeStamp = System.currentTimeMillis()

        val intent = Intent(USER_ACTIVITY_SERVICE_TAG)
            .putExtra(ACCELERATION_KEY, if (accelerationCount != 0) accelerationSum / accelerationCount else 0.0)
            .putExtra(TIMESTAMP_KEY, timeStamp)
            .putExtra(SPEED_KEY, if (speedCount != 0) speedSum / speedCount else 0.0)
            .putExtra(STEPS_KEY, steps)

        sendBroadcast(intent)
    }

    companion object {
        const val USER_ACTIVITY_SERVICE_TAG = "UserActivityService"

        const val TIMESTAMP_KEY = "timestamp"
        const val ACCELERATION_KEY = "acceleration"
        const val SPEED_KEY = "speed"
        const val STEPS_KEY = "steps"

        private const val GRAVITY = 9.80665

        private const val ACCURACY = 1
        private const val MAX_ACCELERATION = 30

        private const val SERVICE_ID = 101

        private const val TIMER_TIME = 5 * 60 * 1000L
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when(event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                updateAcceleration(event.values)
            }
            Sensor.TYPE_STEP_DETECTOR -> {
                updateSteps(event.values.first().toInt())
            }
        }
    }

    private fun updateSteps(value: Int) {
        steps += value
    }

    private fun updateAcceleration(values: FloatArray) {
        val x = values[0]
        val y = values[1]
        val z = values[2]
        val acceleration = sqrt((x.pow(2) + y.pow(2) + z.pow(2))) - GRAVITY

        if (acceleration > ACCURACY && acceleration < MAX_ACCELERATION) {
            accelerationSum += acceleration
            ++accelerationCount
        }
    }

    override fun onLocationChanged(location: Location) {
        if (location.hasSpeed() && location.speed > 0) {
            val speed = location.speed * 3600 / 1000
            speedSum += speed
            ++speedCount
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // No IMPL
    }
}