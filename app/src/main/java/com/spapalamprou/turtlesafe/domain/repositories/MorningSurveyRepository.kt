package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel

/**
 * Repository interface that handles morning survey data.
 */
interface MorningSurveyRepository {

    /**
     * Saves a morning survey to the local database.
     *
     * @param morningSurvey The MorningSurveyModel containing the survey data to be saved.
     * @return The ID of the inserted survey in the database.
     */
    suspend fun saveToDatabase(morningSurvey: MorningSurveyModel): Long

    /**
     * Sends the morning survey data to the server.
     *
     * @param morningSurvey The MorningSurveyModel containing the survey data to be sent.
     */
    suspend fun sendToServer(morningSurvey: MorningSurveyModel)

    /**
     * Starts a background worker to handle tasks related to the morning survey.
     *
     * @param id The ID of the morning survey to be associated with the worker task.
     */
    suspend fun startWorker(id: Long)
}