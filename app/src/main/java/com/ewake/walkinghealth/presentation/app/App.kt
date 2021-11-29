package com.ewake.walkinghealth.presentation.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.ewake.walkinghealth.data.local.prefs.BroadcastPrefs
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.presentation.manager.SendingActivityWorker
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var broadcastPrefs: BroadcastPrefs

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        broadcastPrefs.isReceiverStarted = false
    }
}