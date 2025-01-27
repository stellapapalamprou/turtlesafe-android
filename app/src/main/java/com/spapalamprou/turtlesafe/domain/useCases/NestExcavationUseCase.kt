package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestExcavationRepository
import javax.inject.Inject

/**
 * Use case class to handle the submission of nest excavation.
 *
 * @property nestExcavationRepository Repository responsible to process the nest excavation data.
 */
class NestExcavationUseCase @Inject constructor(
    private val nestExcavationRepository: NestExcavationRepository
) {

    /**
     * Submits the nest excavation data.
     *
     * @param nestExcavation The nest excavation data model to be saved.
     * @param morningSurveyId The ID of the morning survey to which this nest excavation is associated.
     * @return The ID of the nest excavation record saved in the database.
     */
    suspend fun submit(nestExcavation: NestExcavationModel, morningSurveyId: Long): Long {
        return nestExcavationRepository.saveToDatabase(nestExcavation, morningSurveyId)
    }
}