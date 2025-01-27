package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing the data required for the Reset Password action.
 *
 * @property userId A unique ID for each registered user.
 * @property token A token used to verify the reset request.
 * @property password The new password that the user wants to have.
 */
data class ResetPasswordModel(
    val userId: String,
    val token: String,
    val password: String
)