package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel
import com.spapalamprou.turtlesafe.domain.repositories.NestHatchingRepository
import javax.inject.Inject

/**
 * Use case class to handle the submission of nest hatching.
 *
 * @property nestHatchingRepository Repository responsible to process the nest hatching data.
 */
class NestHatchingUseCase @Inject constructor(
    private val nestHatchingRepository: NestHatchingRepository
) {

    /**
     * Submits the nest hatching data.
     *
     * @param nestHatching The nest hatching data model to be saved.
     * @param morningSurveyId The ID of the morning survey to which this nest hatching is associated.
     * @return The ID of the nest hatching record saved in the database.
     */
    suspend fun submit(nestHatching: NestHatchingModel, morningSurveyId: Long): Long {
       return nestHatchingRepository.saveToDatabase(nestHatching, morningSurveyId)
    }
}