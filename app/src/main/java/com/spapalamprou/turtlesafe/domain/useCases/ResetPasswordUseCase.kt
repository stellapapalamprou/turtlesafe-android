package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.ResetPasswordModel
import com.spapalamprou.turtlesafe.domain.repositories.ResetPasswordRepository
import javax.inject.Inject

/**
 * Use case class to handle reset password action.
 *
 * @property resetPasswordRepository The repository responsible to process the reset password data.
 */
class ResetPasswordUseCase @Inject constructor(
    private val resetPasswordRepository: ResetPasswordRepository
) {

    /**
     * Submits reset password data to the server.
     *
     * @param resetPassword The model containing the reset password information.
     */
    suspend fun submit(resetPassword: ResetPasswordModel) {
        resetPasswordRepository.sendToServer(resetPassword)
    }
}