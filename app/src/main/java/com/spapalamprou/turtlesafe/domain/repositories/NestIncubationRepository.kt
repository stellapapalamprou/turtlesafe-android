package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel

/**
 * Repository interface that handles nest incubation data.
 */
interface NestIncubationRepository {

    /**
     * Saves a nest incubation record and its associated incubation data to the local database,
     * and associates it with a morning survey.
     *
     * @param nestIncubation The NestIncubationModel containing the incubation data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest incubation.
     * @return The row ID of the inserted nest incubation record in the database.
     */
    suspend fun saveToDatabase(nestIncubation: NestIncubationModel, morningSurveyId: Long): Long
}