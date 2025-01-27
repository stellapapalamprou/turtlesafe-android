package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestIncubationDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestIncubationRepository
import javax.inject.Inject

/**
 * Implementation of NestIncubationRepository that that handles the management of the nest incubation data.
 *
 * @param nestIncubationDao Data access object for performing database operations related to nest incubation.
 */
class NestIncubationRepositoryImp @Inject constructor(
    private val nestIncubationDao: NestIncubationDao
) : NestIncubationRepository {

    /**
     * Saves a nest incubation record and its associated incubation data to the local database,
     * and associates it with a morning survey.
     *
     * @param nestIncubation The NestIncubationModel containing the incubation data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest incubation.
     * @return The row ID of the inserted nest incubation record in the database.
     */
    override suspend fun saveToDatabase(
        nestIncubation: NestIncubationModel,
        morningSurveyId: Long
    ): Long {

        return nestIncubationDao.insert(
            nestIncubationEntity = nestIncubation.asEntity(morningSurveyId),
            incubationDataEntities = nestIncubation.incubationDataList.map { event ->
                event.asEntity()
            }
        )
    }
}