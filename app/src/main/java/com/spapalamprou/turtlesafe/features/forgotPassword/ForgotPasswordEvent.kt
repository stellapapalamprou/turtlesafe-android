package com.spapalamprou.turtlesafe.features.forgotPassword

/**
 * Sealed class representing the events that can occur in the Forgot Password screen.
 */
sealed class ForgotPasswordEvent {

    /**
     * Event triggered when the email input is changed by the user.
     *
     * @property email The updated email address entered by the user.
     */
    data class EmailChanged(
        val email: String = ""
    ) : ForgotPasswordEvent()

    /**
     * Event triggered when the user clicks the button.
     */
    object SendCodeButtonClicked : ForgotPasswordEvent()
}