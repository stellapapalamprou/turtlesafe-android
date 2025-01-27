package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.SignUpModel

/**
 * Repository interface that handles sign up data.
 */
interface SignUpRepository {

    /**
     * Sends the sign up data to the server.
     *
     * @param signUp The model containing the sign up data to be sent.
     */
    suspend fun sendToServer(signUp: SignUpModel)
}