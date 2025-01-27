package com.spapalamprou.turtlesafe.features.signUp

/**
 * Sealed class representing the events that can occur in the sign up screen.
 */
sealed class SignUpEvent {

    /**
     * Event triggered when the user changes the first name field.
     *
     * @param firstName The updated first name entered by the user.
     */
    data class FirstNameChanged(
        val firstName: String = ""
    ) : SignUpEvent()

    /**
     * Event triggered when the user changes the last name field.
     *
     * @param lastName The updated last name entered by the user.
     */
    data class LastNameChanged(
        val lastName: String = ""
    ) : SignUpEvent()

    /**
     * Event triggered when the user changes the email field.
     *
     * @param email The updated email address entered by the user.
     */
    data class EmailChanged(
        val email: String = ""
    ) : SignUpEvent()

    /**
     * Event triggered when the user changes the password field.
     *
     * @param password The updated password entered by the user.
     */
    data class PasswordChanged(
        val password: String = ""
    ) : SignUpEvent()

    /**
     * Event triggered when the user clicks the sign up button to submit the form.
     */
    object SignUpButtonClicked : SignUpEvent()
}