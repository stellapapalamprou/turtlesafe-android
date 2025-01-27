package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel

/**
 * Repository interface that handles nest relocation data.
 */
interface NestRelocationRepository {

    /**
     * Saves a nest relocation record to the local database and associates it with a morning survey.
     *
     * @param nestRelocation The NestRelocationModel containing the relocation data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest relocation.
     * @return The row ID of the inserted nest relocation record in the database.
     */
    suspend fun saveToDatabase(nestRelocation: NestRelocationModel, morningSurveyId: Long): Long
}