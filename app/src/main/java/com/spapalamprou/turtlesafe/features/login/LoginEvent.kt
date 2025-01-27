package com.spapalamprou.turtlesafe.features.login

/**
 * Sealed class representing the different user events that can occur in the login screen.
 */
sealed class LoginEvent {

    /**
     * Event representing a change in the email field.
     *
     * @property email The updated email entered by the user.
     */
    data class EmailChanged(
        val email: String = ""
    ) : LoginEvent()

    /**
     * Event representing a change in the password field.
     *
     * @property password The updated password entered by the user.
     */
    data class PasswordChanged(
        val password: String = ""
    ) : LoginEvent()

    /**
     * Event representing the action of clicking the login button.
     */
    object LoginButtonClicked : LoginEvent()
}