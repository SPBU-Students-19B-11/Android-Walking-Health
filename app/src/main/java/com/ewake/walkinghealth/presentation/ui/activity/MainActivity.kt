package com.ewake.walkinghealth.presentation.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.ewake.walkinghealth.R
import com.ewake.walkinghealth.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_host) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) ==
            PackageManager.PERMISSION_DENIED) permissions.add(Manifest.permission.ACTIVITY_RECOGNITION)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_DENIED) permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissions.isNotEmpty()) requestPermissions(permissions.toTypedArray(), 100)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createServiceNotificationsChannel()
    }

    @SuppressLint("NewApi")
    private fun createServiceNotificationsChannel() {
        val name = "Уведомления отслеживания пользовательской активности"
        val descriptionText = "Показ уведомления на отслеживание пользовательской актисности"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(SERVICE_NOTIFICATION_CHANNEL, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val SERVICE_NOTIFICATION_CHANNEL = "ServiceNotification"
    }
}