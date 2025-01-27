package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel

/**
 * Repository interface that handles night survey data.
 */
interface NightSurveyRepository {

    /**
     * Saves a night survey and its associated tag data (old and new tags) to the local database.
     *
     * @param nightSurvey The NightSurveyModel containing the survey data, including old and new tags.
     * @return The row ID of the inserted night survey record in the database.
     */
    suspend fun saveToDatabase(nightSurvey: NightSurveyModel): Long

    /**
     * Sends the night survey data to the server.
     *
     * @param nightSurvey The NightSurveyModel containing the survey data to be sent.
     */
    suspend fun sendToServer(nightSurvey: NightSurveyModel)

    /**
     * Starts a background worker to handle tasks related to the night survey.
     *
     * @param id The ID of the night survey record to be associated with the worker task.
     */
    suspend fun startWorker(id: Long)
}