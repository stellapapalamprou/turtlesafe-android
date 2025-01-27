package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestIncubationRepository
import javax.inject.Inject

/**
 * Use case class to handle the submission of nest incubation.
 *
 * @property nestIncubationRepository Repository responsible to process the nest incubation data.
 */
class NestIncubationUseCase @Inject constructor(
    private val nestIncubationRepository: NestIncubationRepository
) {

    /**
     * Submits the nest incubation data.
     *
     * @param nestIncubation The nest incubation data model to be saved.
     * @param morningSurveyId The ID of the morning survey to which this nest incubation is associated.
     * @return The ID of the nest incubation record saved in the database.
     */
    suspend fun submit(nestIncubation: NestIncubationModel, morningSurveyId: Long): Long {
        return nestIncubationRepository.saveToDatabase(nestIncubation, morningSurveyId)
    }
}