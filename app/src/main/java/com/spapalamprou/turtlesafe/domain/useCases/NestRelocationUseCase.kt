package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestRelocationRepository
import javax.inject.Inject

/**
 * Use case class to handle the submission of nest relocation.
 *
 * @property nestRelocationRepository Repository responsible to process the nest relocation data.
 */
class NestRelocationUseCase @Inject constructor(
    private val nestRelocationRepository: NestRelocationRepository
) {

    /**
     * Submits the nest relocation data.
     *
     * @param nestRelocation The nest relocation data model to be saved.
     * @param morningSurveyId The ID of the morning survey to which this nest relocation is associated.
     * @return The ID of the nest relocation record saved in the database.
     */
    suspend fun submit(nestRelocation: NestRelocationModel, morningSurveyId: Long): Long {
        return nestRelocationRepository.saveToDatabase(nestRelocation, morningSurveyId)
    }
}