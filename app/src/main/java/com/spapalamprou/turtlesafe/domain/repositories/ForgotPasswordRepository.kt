package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.ForgotPasswordModel

/**
 * Repository interface that handles forgot password data.
 *
 */
interface ForgotPasswordRepository {

    /**
     * Sends the forgot password request to the server.
     *
     * @param forgotPassword The model containing the forgot password information.
     */
    suspend fun sendToServer(forgotPassword: ForgotPasswordModel)
}