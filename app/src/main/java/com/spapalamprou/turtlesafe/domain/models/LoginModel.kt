package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing the data required for the user's login.
 *
 * @property email The user's email address.
 * @property password The user's password.
 *
 */
data class LoginModel(
    val email: String,
    val password: String
)