package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.SignUpModel
import com.spapalamprou.turtlesafe.domain.repositories.SignUpRepository
import javax.inject.Inject

/**
 * Use case class to handle user sign up.
 *
 * @property signUpRepository The repository responsible to process the sign up data.
 */
class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {

    /**
     * Submits sign up data to the server.
     *
     * @param signUp The model containing the user's sign up information.
     */
    suspend fun submit(signUp: SignUpModel) {
        signUpRepository.sendToServer(signUp)
    }
}