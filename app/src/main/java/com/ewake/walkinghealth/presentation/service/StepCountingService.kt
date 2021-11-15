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

class StepCountingService : Service(), SensorEventListener {

    private val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as? SensorManager }
    private val stepDetectorSensor by lazy { sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) }

    private var tempSteps = 0
        set(value) {
            field = value
            if (value >= STEPS_DELAY) {
                notifyStepsChanges()
            }
        }

    private val notification: Notification by lazy {
        NotificationCompat.Builder(this, MainActivity.SERVICE_NOTIFICATION_CHANNEL)
            .setContentTitle("Считаем ваши шаги")
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(STEP_COUNTING_SERVICE_TAG, "Starting")

        val result = sensorManager?.registerListener(this, stepDetectorSensor, 0)
        if (result != true) {
            stopSelf()
        }
        startForeground(SERVICE_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(STEP_COUNTING_SERVICE_TAG, "Stopping")
        sensorManager?.unregisterListener(this, stepDetectorSensor)
        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            tempSteps += event.values.first().toInt()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun notifyStepsChanges() {
        Log.i(STEP_COUNTING_SERVICE_TAG, "Add steps: $tempSteps")
        val intent = Intent(STEP_COUNTING_SERVICE_TAG).putExtra(STEPS_KEY, tempSteps)
        sendBroadcast(intent)
        tempSteps = 0
    }

    companion object {
        const val STEPS_KEY = "steps"

        const val STEP_COUNTING_SERVICE_TAG = "StepCountingService"

        private const val STEPS_DELAY = 10

        private const val SERVICE_ID = 103
    }
}