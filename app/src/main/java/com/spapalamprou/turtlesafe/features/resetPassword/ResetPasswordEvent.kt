package com.spapalamprou.turtlesafe.features.resetPassword

/**
 * Sealed class representing the events that can occur in the reset password screen.
 */
sealed class ResetPasswordEvent {

    /**
     * Event triggered when the user ID input changes.
     *
     * @property userId The updated user ID entered by the user.
     */
    data class UserIdChanged(
        val userId: String = "",
    ) : ResetPasswordEvent()

    /**
     * Event triggered when the token input changes.
     *
     * @property token The updated token entered by the user.
     */
    data class TokenChanged(
        val token: String = "",
    ) : ResetPasswordEvent()

    /**
     * Event triggered when the password input changes.
     *
     * @property password The updated password entered by the user.
     */
    data class PasswordChanged(
        val password: String = "",
    ) : ResetPasswordEvent()

    /**
     * Event triggered when the submit button is clicked.
     */
    object SubmitButtonClicked : ResetPasswordEvent()
}