package com.spapalamprou.turtlesafe.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.spapalamprou.turtlesafe.data.api.NightSurveyApi
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.database.dao.NightSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.asModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * A worker that handles the background processing of night surveys in offline mode.
 *
 * @property nightSurveyDao The DAO for accessing the night survey data from the local database.
 * @property nightSurveyApi The API for uploading night survey data to the server.
 * @property appContext The application context.
 * @property params The parameters required to configure the worker.
 */
@HiltWorker
class NightSurveyWorker @AssistedInject constructor(
    private val nightSurveyDao: NightSurveyDao,
    private val nightSurveyApi: NightSurveyApi,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val nightSurveyId = inputData.getLong("nightSurveyId", -1)
        if (nightSurveyId == -1L) return Result.failure()
        val nightSurveys = nightSurveyDao.getNightSurvey(nightSurveyId)
        if (nightSurveys.isNotEmpty()) {
            val model = nightSurveys.first().asModel()
            try {
                nightSurveyApi.createNightSurvey(model.asJson())
            } catch (exception: UnknownHostException) {
                return Result.retry()
            } catch (exception: ConnectException) {
                return Result.retry()
            }
            return Result.success()
        } else return Result.failure()
    }
}