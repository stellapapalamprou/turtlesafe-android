package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.LoginModel
import com.spapalamprou.turtlesafe.domain.models.TokenModel

/**
 * Repository interface that handles login data.
 */
interface LoginRepository {

    /**
     * Sends login credentials to the server and retrieves authentication tokens.
     *
     * @param login The model containing the user's login credentials.
     * @return TokenModel The model containing access and refresh tokens.
     */
    suspend fun sendToServer(login: LoginModel): TokenModel

    /**
     * Saves authentication tokens to the local storage.
     *
     * @param tokens The model containing the access and refresh tokens to be saved.
     */
    fun saveToStorage(tokens: TokenModel)

    /**
     * Checks if authentication tokens are already saved in storage.
     *
     * @return true if tokens are saved, false otherwise.
     */
    fun areTokensSaved(): Boolean

    /**
     * Deletes authentication tokens from the storage.
     */
    fun deleteTokensFromStorage()
}