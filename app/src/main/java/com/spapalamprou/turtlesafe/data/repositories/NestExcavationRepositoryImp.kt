package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestExcavationDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestExcavationRepository
import javax.inject.Inject

/**
 * Implementation of NestExcavationRepository that handles the management of the nest excavation data.
 *
 * @param nestExcavationDao Data access object for performing database operations on local database.
 */
class NestExcavationRepositoryImp @Inject constructor(
    private val nestExcavationDao: NestExcavationDao
) : NestExcavationRepository {

    /**
     * Saves a nest excavation record to the local database and associates it with a morning survey.
     *
     * @param nestExcavation The NestExcavationModel containing the excavation data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest excavation.
     * @return The row ID of the inserted survey in the database.
     */
    override suspend fun saveToDatabase(
        nestExcavation: NestExcavationModel,
        morningSurveyId: Long
    ): Long {
        return nestExcavationDao.insert(nestExcavation.asEntity(morningSurveyId))
    }
}