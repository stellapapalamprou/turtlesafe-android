package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.ResetPasswordModel

/**
 * Repository interface that handles reset password data.
 */
interface ResetPasswordRepository {

    /**
     * Sends the reset password data to the server.
     *
     * @param resetPassword The model containing the reset password data to be sent.
     */
    suspend fun sendToServer(resetPassword: ResetPasswordModel)
}