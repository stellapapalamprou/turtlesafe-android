package com.spapalamprou.turtlesafe.data.workers

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Interface for managing the execution of the NightSurveyWorkerManager.
 */
interface NightSurveyWorkerManager {

    /**
     * Starts the NightSurveyWorker.
     *
     * @param id The ID of the night survey to be processed by the worker.
     */
    fun startWorker(id: Long)
}

/**
 * Implementation of the NightSurveyWorkerManager interface which manages the NightSurveyWorker.
 *
 * @property context The application context used.
 */
class NightSurveyWorkerManagerImp @Inject constructor(@ApplicationContext val context: Context) :
    NightSurveyWorkerManager {
    override fun startWorker(id: Long) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = OneTimeWorkRequestBuilder<NightSurveyWorker>()
            .setInputData(
                Data.Builder()
                    .putLong("nightSurveyId", id)
                    .build()
            )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag("NightSurveyWorker")
            .build()

        WorkManager.getInstance(context)
            .enqueue(request)
    }
}