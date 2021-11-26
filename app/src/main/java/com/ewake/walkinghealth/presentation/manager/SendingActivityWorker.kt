package com.ewake.walkinghealth.presentation.manager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ewake.walkinghealth.data.api.model.request.MedicalSendDataRequest
import com.ewake.walkinghealth.data.api.model.request.SendMessageRequest
import com.ewake.walkinghealth.data.api.model.response.isSuccess
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import com.ewake.walkinghealth.presentation.app.App
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltWorker
class SendingActivityWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val medicalRepository: MedicalRepository,
    appDatabase: AppDatabase
) : CoroutineWorker(context, workerParameters) {

    private val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

    private val dao = appDatabase.getUserActivityDao()

    override suspend fun doWork(): Result {
        Log.d(TAG, "Sending starting")

        //TODO
        val userActivities = dao.getAll()

        return Result.success()
    }

    //TODO
    private suspend fun sendData(request: MedicalSendDataRequest): Boolean {
        Log.d(TAG, "Send data from: ${request.date}")
        val result = medicalRepository.sendData(request).isSuccess()

        if (result && request.date != currentDate) {
            dao.delete(request.date)
        }

        return result
    }

    companion object {
        private const val TAG = "SendingActivityWorker"
    }
}