package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestRelocationDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestRelocationRepository
import javax.inject.Inject

/**
 * Implementation of NestRelocationRepository that handles the management of nest relocation data.
 *
 * @param nestRelocationDao Data access object for performing database operations on local database.
 */
class NestRelocationRepositoryImp @Inject constructor(
    private val nestRelocationDao: NestRelocationDao
) : NestRelocationRepository {

    /**
     * Saves a nest relocation record to the local database and associates it with a morning survey.
     *
     * @param nestRelocation The NestRelocationModel containing the relocation data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest relocation.
     * @return The row ID of the inserted nest relocation record in the database.
     */
    override suspend fun saveToDatabase(
        nestRelocation: NestRelocationModel,
        morningSurveyId: Long
    ): Long {
        return nestRelocationDao.insert(nestRelocation.asEntity(morningSurveyId))
    }
}