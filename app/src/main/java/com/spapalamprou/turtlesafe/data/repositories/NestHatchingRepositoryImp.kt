package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestHatchingDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel
import com.spapalamprou.turtlesafe.domain.repositories.NestHatchingRepository
import javax.inject.Inject

/**
 * Implementation of NestHatchingRepository that handles the management of the nest hatching data.
 *
 * @param nestHatchingDao Data access object for performing database operations on local database.
 */
class NestHatchingRepositoryImp @Inject constructor(
    private val nestHatchingDao: NestHatchingDao
) : NestHatchingRepository {

    /**
     * Saves a nest hatching record to the local database and associates it with a morning survey.
     *
     * @param nestHatching The NestHatchingModel containing the hatching data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest hatching.
     * @return The row ID of the inserted survey in the database.
     */
    override suspend fun saveToDatabase(
        nestHatching: NestHatchingModel,
        morningSurveyId: Long
    ): Long {

        return nestHatchingDao.insert(
            nestHatchingEntity = nestHatching.asEntity(morningSurveyId),
            hatchingDataEntities = nestHatching.hatchingDataList.map { event ->
                event.asEntity()
            }
        )
    }
}