package com.spapalamprou.turtlesafe.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface representing the API for refreshing JWT tokens.
 */
interface RefreshTokenApi {

    /**
     * Sends a request to refresh the access token using the provided refresh token.
     *
     * @param json The RefreshTokenJson containing the refresh token.
     * @return The RefreshTokenJsonResponse containing the new access token.
     */
    @POST("auth/jwt/refresh/")
    suspend fun refreshToken(@Body json: RefreshTokenJson): RefreshTokenJsonResponse
}

/**
 * Data class representing the request body for refreshing a JWT token.
 *
 * @property refreshToken The refresh token used to request a new access token.
 */
data class RefreshTokenJson(
    @SerializedName("refresh") val refreshToken: String
)

/**
 * Data class representing the response from the server when a new access token is generated.
 *
 * @property accessToken The new access token provided by the server.
 */
data class RefreshTokenJsonResponse(
    @SerializedName("access") val accessToken: String
)