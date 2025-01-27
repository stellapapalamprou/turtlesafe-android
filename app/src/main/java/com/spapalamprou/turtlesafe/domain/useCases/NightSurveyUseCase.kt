package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.repositories.NightSurveyRepository
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Use case class to handle the submission of a night survey.
 *
 * @property nightSurveyRepository Repository responsible to process the night survey data.
 */
class NightSurveyUseCase @Inject constructor(
    private val nightSurveyRepository: NightSurveyRepository
) {

    /**
     * Submits the night survey data. If the upload fails it schedules
     * a worker to retry the upload.
     *
     * @param nightSurvey The night survey data model to be submitted.
     */
    suspend fun submit(nightSurvey: NightSurveyModel) {
        val nightSurveyId = nightSurveyRepository.saveToDatabase(nightSurvey)
        try {
            nightSurveyRepository.sendToServer(nightSurvey)
        } catch (exception: UnknownHostException) {
            nightSurveyRepository.startWorker(nightSurveyId)
        } catch (exception: ConnectException) {
            nightSurveyRepository.startWorker(nightSurveyId)
        }
    }
}