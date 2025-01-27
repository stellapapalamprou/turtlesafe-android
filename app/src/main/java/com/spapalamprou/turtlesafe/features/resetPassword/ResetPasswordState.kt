package com.spapalamprou.turtlesafe.features.resetPassword

/**
 * Data class representing the state of the reset password screen.
 *
 * @property userId The user's ID.
 * @property invalidUserIdMessage The validation message for the user ID, indicating any errors.
 * @property token The token entered in the form for password reset.
 * @property invalidTokenMessage The validation message for the token, indicating any errors.
 * @property password The new password entered by the user.
 * @property invalidPasswordMessage The validation message for the password, indicating any errors.
 */
data class ResetPasswordState(
    val userId: String = "",
    val invalidUserIdMessage: String = "",
    val token: String = "",
    val invalidTokenMessage: String = "",
    val password: String = "",
    val invalidPasswordMessage: String = ""
)