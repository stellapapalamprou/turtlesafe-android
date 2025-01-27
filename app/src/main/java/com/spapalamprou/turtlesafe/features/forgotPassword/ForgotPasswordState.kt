package com.spapalamprou.turtlesafe.features.forgotPassword

/**
 * Data class that represents the state of the Forgot Password screen.
 *
 * @property email The email address entered by the user.
 * @property invalidEmailMessage A message indicating whether the entered email is invalid.
 */
data class ForgotPasswordState(
    val email: String = "",
    val invalidEmailMessage: String = ""
)