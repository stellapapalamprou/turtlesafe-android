package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.domain.models.SignUpModel
import com.spapalamprou.turtlesafe.domain.repositories.SignUpRepository
import javax.inject.Inject

/**
 * Implementation of SignUpRepository that handles user registration by communicating with the server.
 *
 * @param authenticationApi The API service to handle user registration requests.
 */
class SignUpRepositoryImp @Inject constructor(
    private val authenticationApi: AuthenticationApi
) : SignUpRepository {

    /**
     * Sends user sign-up data to the server for creating a new user account.
     *
     * @param signUp The SignUpModel containing the user's registration information.
     */
    override suspend fun sendToServer(signUp: SignUpModel) {
        authenticationApi.createUser(signUp.asJson())
    }
}