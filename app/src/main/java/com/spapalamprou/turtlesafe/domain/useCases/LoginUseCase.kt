package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.LoginModel
import com.spapalamprou.turtlesafe.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case class to handle user login.
 *
 * @property loginRepository The repository responsible to process login data.
 */
@Singleton
class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    val isUserLoggedIn = MutableStateFlow(loginRepository.areTokensSaved())

    /**
     * Submits the login credentials to the server, receives authentication tokens, and
     * stores them to local database.
     *
     * @param login The login data to be submitted.
     */
    suspend fun submit(login: LoginModel) {
        val tokens = loginRepository.sendToServer(login)
        loginRepository.saveToStorage(tokens)
        isUserLoggedIn.update { loginRepository.areTokensSaved() }
    }

    /**
     * Logs out the user from the app.
     */
    fun logout() {
        loginRepository.deleteTokensFromStorage()
        isUserLoggedIn.update { loginRepository.areTokensSaved() }
    }
}