package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.api.asModel
import com.spapalamprou.turtlesafe.data.storage.TokenStorage
import com.spapalamprou.turtlesafe.domain.models.LoginModel
import com.spapalamprou.turtlesafe.domain.models.TokenModel
import com.spapalamprou.turtlesafe.domain.repositories.LoginRepository
import javax.inject.Inject

/**
 * Implementation of LoginRepository that handles user authentication and token management.
 *
 * @param authenticationApi The API service used to authenticate the user and obtain tokens.
 * @param tokenStorage The storage where the tokens are saved.
 */
class LoginRepositoryImp @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val tokenStorage: TokenStorage
) : LoginRepository {

    /**
     * Sends the user login information to the server and retrieves the authentication token.
     *
     * @param login The LoginModel containing the user's login credentials.
     * @return TokenModel containing the access and refresh tokens obtained from the server.
     */
    override suspend fun sendToServer(login: LoginModel): TokenModel {
        val response = authenticationApi.login(login.asJson())
        return response.asModel()
    }

    /**
     * Stores the authentication tokens in the token storage.
     *
     * @param tokens The TokenModel containing the access and refresh tokens to be saved.
     */
    override fun saveToStorage(tokens: TokenModel) {
        tokenStorage.setAccessToken(tokens.access)
        tokenStorage.setRefreshToken(tokens.refresh)
    }

    /**
     * Checks if the authentication tokens are already saved in the storage.
     *
     * @return `true` if both access and refresh tokens are saved in the storage, `false` otherwise.
     */
    override fun areTokensSaved(): Boolean {
        return tokenStorage.getAccessToken() != null &&
                tokenStorage.getRefreshToken() != null
    }

    /**
     * Deletes all stored authentication tokens from the storage.
     */
    override fun deleteTokensFromStorage() {
        tokenStorage.deleteAll()
    }
}