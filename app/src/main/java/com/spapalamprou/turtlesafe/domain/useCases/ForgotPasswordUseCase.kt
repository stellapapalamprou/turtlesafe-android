package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.ForgotPasswordModel
import com.spapalamprou.turtlesafe.domain.repositories.ForgotPasswordRepository
import javax.inject.Inject

/**
 * Use case class to handle forgot password action.
 *
 * @property forgotPasswordRepository The repository responsible to process the forgot password data.
 */
class ForgotPasswordUseCase @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordRepository
) {

    /**
     * Submits forgot password data to the server.
     *
     * @param resetPassword The model containing the forgot password information.
     */
    suspend fun submit(forgotPassword: ForgotPasswordModel) {
        forgotPasswordRepository.sendToServer(forgotPassword)
    }
}