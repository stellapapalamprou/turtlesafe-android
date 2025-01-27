package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.NightSurveyApi
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.database.dao.NightSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.data.workers.NightSurveyWorkerManager
import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.repositories.NightSurveyRepository
import javax.inject.Inject

/**
 * Implementation of NightSurveyRepository that handles the management of night survey data.
 *
 * @param nightSurveyDao Data access object for performing database operations on local database.
 * @param nightSurveyApi API service used to send night survey data to the server.
 * @param nightSurveyWorkerManager Manager for handling background workers related to night survey.
 */
class NightSurveyRepositoryImp @Inject constructor(
    private val nightSurveyDao: NightSurveyDao,
    private val nightSurveyApi: NightSurveyApi,
    private val nightSurveyWorkerManager: NightSurveyWorkerManager
) : NightSurveyRepository {

    /**
     * Saves a night survey and its associated tag data (old and new tags) to the local database.
     *
     * @param nightSurvey The NightSurveyModel containing the survey data, including old and new tags.
     * @return The row ID of the inserted night survey record in the database.
     */
    override suspend fun saveToDatabase(nightSurvey: NightSurveyModel): Long {
        val nightSurveyId = nightSurveyDao.insert(
            nightSurveyEntity = nightSurvey.asEntity(),
            oldTagEntities = nightSurvey.oldTagDataList.map { tag ->
                tag.asEntity()
            },
            newTagEntities = nightSurvey.newTagDataList.map { tag ->
                tag.asEntity()
            }
        )
        return nightSurveyId
    }

    /**
     * Sends the night survey data to the server.
     *
     * @param nightSurvey The NightSurveyModel containing the survey data to be sent.
     */
    override suspend fun sendToServer(nightSurvey: NightSurveyModel) {
        nightSurveyApi.createNightSurvey(nightSurvey.asJson())
    }

    /**
     * Starts a background worker to handle tasks related to the night survey.
     *
     * @param id The ID of the night survey record to be associated with the worker task.
     */
    override suspend fun startWorker(id: Long) {
        nightSurveyWorkerManager.startWorker(id)
    }
}