package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.MorningSurveyApi
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.database.dao.MorningSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.data.workers.MorningSurveyWorkerManager
import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel
import com.spapalamprou.turtlesafe.domain.repositories.MorningSurveyRepository
import javax.inject.Inject

/**
 * Implementation of MorningSurveyRepository that handles the management of morning survey data.
 *
 * @param morningSurveyDao Data access object for performing database operations on local database.
 * @param morningSurveyApi API service used to send morning survey data to the server.
 * @param morningSurveyWorkerManager Manager for handling background workers related to morning survey.
 */
class MorningSurveyRepositoryImp @Inject constructor(
    private val morningSurveyDao: MorningSurveyDao,
    private val morningSurveyApi: MorningSurveyApi,
    private val morningSurveyWorkerManager: MorningSurveyWorkerManager
) : MorningSurveyRepository {

    /**
     * Saves a morning survey to the local database.
     *
     * @param morningSurvey The MorningSurveyModel containing the survey data to be saved.
     * @return The ID of the inserted survey in the database.
     */
    override suspend fun saveToDatabase(morningSurvey: MorningSurveyModel): Long {
        return morningSurveyDao.insert(morningSurvey.asEntity())
    }

    /**
     * Sends the morning survey data to the remote server.
     *
     * @param morningSurvey The MorningSurveyModel containing the survey data to be sent.
     */
    override suspend fun sendToServer(morningSurvey: MorningSurveyModel) {
        morningSurveyApi.createMorningSurvey(morningSurvey.asJson())
    }

    /**
     * Starts a background worker to handle tasks related to the morning survey.
     *
     * @param id The ID of the morning survey to be associated with the worker task.
     */
    override suspend fun startWorker(id: Long) {
        morningSurveyWorkerManager.startWorker(id)
    }
}