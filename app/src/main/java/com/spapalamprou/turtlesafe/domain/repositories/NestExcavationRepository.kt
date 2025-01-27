package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel

/**
 * Repository interface that handles nest excavation data.
 */
interface NestExcavationRepository {

    /**
     * Saves a nest excavation record to the local database and associates it with a morning survey.
     *
     * @param nestExcavation The NestExcavationModel containing the excavation data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest excavation.
     * @return The row ID of the inserted survey in the database.
     */
    suspend fun saveToDatabase(nestExcavation: NestExcavationModel, morningSurveyId: Long): Long
}