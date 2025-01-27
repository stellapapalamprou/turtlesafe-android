package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel
import com.spapalamprou.turtlesafe.domain.repositories.MorningSurveyRepository
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Use case class to handle the submission of a morning survey.
 *
 * @property morningSurveyRepository Repository responsible to process the morning survey data.
 * @property newNestRepository Repository to manage new nest data.
 * @property newNestUseCase Use case for handling new nest submission.
 * @property nestIncubationUseCase Use case for handling nest incubation submission.
 * @property nestRelocationUseCase Use case for handling nest relocation submission.
 * @property nestHatchingUseCase Use case for handling nest hatching submission.
 * @property nestExcavationUseCase Use case for handling nest excavation submission.
 */
class MorningSurveyUseCase @Inject constructor(
    private val morningSurveyRepository: MorningSurveyRepository,
    private val newNestRepository: NewNestRepository,
    private val newNestUseCase: NewNestUseCase,
    private val nestIncubationUseCase: NestIncubationUseCase,
    private val nestRelocationUseCase: NestRelocationUseCase,
    private val nestHatchingUseCase: NestHatchingUseCase,
    private val nestExcavationUseCase: NestExcavationUseCase
) {

    /**
     * Submits the morning survey data.If the upload fails it schedules
     * a worker to retry the upload.
     *
     * @param morningSurvey The morning survey data model to be submitted.
     */
    suspend fun submit(morningSurvey: MorningSurveyModel) {
        val morningSurveyId = morningSurveyRepository.saveToDatabase(morningSurvey)
        for (nest in morningSurvey.newNestList) {
            newNestUseCase.submit(nest, morningSurveyId)
        }
        for (nest in morningSurvey.nestIncubationList) {
            nestIncubationUseCase.submit(nest, morningSurveyId)
        }
        for (nest in morningSurvey.nestRelocationList) {
            nestRelocationUseCase.submit(nest, morningSurveyId)
        }
        for (nest in morningSurvey.nestHatchingList) {
            nestHatchingUseCase.submit(nest, morningSurveyId)
        }
        for (nest in morningSurvey.nestExcavationList) {
            nestExcavationUseCase.submit(nest, morningSurveyId)
        }

        try {
            morningSurveyRepository.sendToServer(morningSurvey)

            for (nest in morningSurvey.newNestList) {
                newNestRepository.sendPhotoToServer(nest)
            }
        } catch (exception: UnknownHostException) {
            morningSurveyRepository.startWorker(morningSurveyId)
        } catch (exception: ConnectException) {
            morningSurveyRepository.startWorker(morningSurveyId)
        }
    }
}