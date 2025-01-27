package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.LoginModel
import com.spapalamprou.turtlesafe.domain.models.SignUpModel
import com.spapalamprou.turtlesafe.domain.models.TokenModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface representing the authentication API endpoints for user login and registration.
 */
interface AuthenticationApi {

    /**
     * Endpoint to authenticate users and create JWT tokens.
     *
     * @param json The login credentials.
     * @return the json response containing the access and refresh tokens.
     */
    @POST("auth/jwt/create/")
    suspend fun login(@Body json: LoginJson): LoginJsonResponse

    /**
     * Endpoint to create a new user account.
     *
     * @param json The user details for registration.
     * @return the json response containing the user's details.
     */
    @POST("auth/users/")
    suspend fun createUser(@Body json: SignUpJson): SignUpJsonResponse
}

/**
 * Data class representing the login request payload.
 *
 * @property email The user's email address.
 * @property password The user's password.
 */
data class LoginJson(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

/**
 * Extension function to serialize a LoginModel to a LoginJson.
 *
 * @return The json representation of the login model.
 */
fun LoginModel.asJson(): LoginJson {
    return LoginJson(
        email = email,
        password = password
    )
}

/**
 * Data class representing the json response of a successful login.
 *
 * @property accessToken The JWT access token.
 * @property refreshToken The JWT refresh token.
 */
data class LoginJsonResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String
)

/**
 * Extension function to deserialize the json response to a TokenModel.
 *
 * @return The TokenModel representation of the login response.
 */
fun LoginJsonResponse.asModel(): TokenModel {
    return TokenModel(
        access = accessToken,
        refresh = refreshToken
    )
}

/**
 * Data class representing the sign up request payload.
 *
 * @property firstName The user's first name.
 * @property lastName The user's last name.
 * @property email The user's email address.
 * @property password The user's password.
 */
data class SignUpJson(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

/**
 * Extension function to serialize a SignUpModel to a SignUpJson.
 *
 * @return The SignUpJson representation of the SignUpModel.
 */
fun SignUpModel.asJson(): SignUpJson {
    return SignUpJson(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password
    )
}


/**
 * Data class representing the json response of a successful user registration.
 *
 * @property firstName The user's first name.
 * @property lastName The user's last name.
 * @property email The user's email address.
 */
data class SignUpJsonResponse(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String
)