package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel

/**
 * Repository interface that handles nest hatching data.
 */
interface NestHatchingRepository {

    /**
     * Saves a nest hatching record to the local database and associates it with a morning survey.
     *
     * @param nestHatching The NestHatchingModel containing the hatching data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest hatching.
     * @return The row ID of the inserted survey in the database.
     */
    suspend fun saveToDatabase(nestHatching: NestHatchingModel, morningSurveyId: Long): Long
}