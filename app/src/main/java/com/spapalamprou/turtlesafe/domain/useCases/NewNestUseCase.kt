package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NewNestModel
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import javax.inject.Inject

/**
 * Use case class to handle the submission of a new nest.
 *
 * @property newNestRepository Repository responsible to process the new nest data.
 */
class NewNestUseCase @Inject constructor(
    private val newNestRepository: NewNestRepository
) {

    /**
     * Submits the new nest data.
     *
     * @param newNest The new nest data model to be saved.
     * @param morningSurveyId The ID of the morning survey to which the new nest is associated.
     * @return The ID of the new nest record saved in the database.
     */
    suspend fun submit(newNest: NewNestModel, morningSurveyId: Long): Long {
        return newNestRepository.saveToDatabase(newNest, morningSurveyId)
    }
}