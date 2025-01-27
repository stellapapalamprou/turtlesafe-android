package com.spapalamprou.turtlesafe.data.authenticator

import com.spapalamprou.turtlesafe.data.api.RefreshTokenApi
import com.spapalamprou.turtlesafe.data.api.RefreshTokenJson
import com.spapalamprou.turtlesafe.data.storage.TokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A class responsible for authenticating HTTP requests by refreshing the access token.
 * It uses a refresh token to acquire a new access token and retry the request.
 *
 * @param refreshTokenApi The API used to refresh the access token using a refresh token.
 * @param tokenStorage The storage for accessing and storing tokens (refresh token and access token).
 */
@Singleton
class RequestAuthenticator @Inject constructor(
    private val refreshTokenApi: RefreshTokenApi,
    private val tokenStorage: TokenStorage
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken == null) return null

        try {
            val refreshTokenResponse = runBlocking {
                refreshTokenApi.refreshToken(RefreshTokenJson(refreshToken))
            }
            tokenStorage.setAccessToken(refreshTokenResponse.accessToken)
            return response.request.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "Bearer ${refreshTokenResponse.accessToken}")
                .build()
        } catch (exception: Exception) {
            return null
        }
    }
}