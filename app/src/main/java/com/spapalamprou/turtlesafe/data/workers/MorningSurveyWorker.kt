package com.spapalamprou.turtlesafe.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.spapalamprou.turtlesafe.data.api.MorningSurveyApi
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.database.dao.MorningSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.asModel
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * A worker that handles the background processing of morning surveys in offline mode.
 *
 * @param morningSurveyDao The DAO for accessing morning survey data in the local database.
 * @param morningSurveyApi The API service for sending morning survey data to the server.
 * @param newNestRepository The repository for managing new nest-related operations.
 * @param appContext The application context.
 * @param params The worker parameters containing input data for the worker.
 */
@HiltWorker
class MorningSurveyWorker @AssistedInject constructor(
    private val morningSurveyDao: MorningSurveyDao,
    private val morningSurveyApi: MorningSurveyApi,
    private val newNestRepository: NewNestRepository,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val morningSurveyId = inputData.getLong("morningSurveyId", -1)
        if (morningSurveyId == -1L) return Result.failure()
        val morningSurveys = morningSurveyDao.getMorningSurvey(morningSurveyId)
        if (morningSurveys.isNotEmpty()) {
            val model = morningSurveys.first().asModel()
            try {
                morningSurveyApi.createMorningSurvey(model.asJson())
                for (nest in model.newNestList) {
                    newNestRepository.sendPhotoToServer(nest)
                }
            } catch (exception: UnknownHostException) {
                return Result.retry()
            } catch (exception: ConnectException) {
                return Result.retry()
            }
            return Result.success()
        } else return Result.failure()
    }
}