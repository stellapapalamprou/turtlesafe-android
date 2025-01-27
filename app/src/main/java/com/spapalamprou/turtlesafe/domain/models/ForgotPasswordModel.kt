package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing the data required for the Forgot Password action.
 *
 * @property email The user's email address.
 *
 */
data class ForgotPasswordModel(
    val email: String
)