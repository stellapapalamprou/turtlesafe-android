package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing the token data.
 *
 * @property access The access token used for authorizing requests.
 * @property refresh The refresh token used to obtain a new access token.
 */
data class TokenModel(
    val access: String,
    val refresh: String
)