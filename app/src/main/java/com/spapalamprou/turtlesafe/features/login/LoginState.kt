package com.spapalamprou.turtlesafe.features.login

import com.spapalamprou.turtlesafe.domain.models.LoginModel

/**
 * Data class representing the state of the login screen.
 *
 * @property email The email entered by the user.
 * @property invalidEmailMessage The validation error message for the email field.
 * @property password The password entered by the user.
 * @property invalidPasswordMessage The validation error message for the password field.
 * @property exception Any exception that might be thrown.
 */
data class LoginState(
    val email: String = "",
    val invalidEmailMessage: String = "",
    val password: String = "",
    val invalidPasswordMessage: String = "",
    val exception: Exception? = null
) {

    /**
     * Transforms the current state into a LoginModel.
     *
     * @return A LoginModel containing the email and password.
     */
    fun transformData(): LoginModel {
        return LoginModel(
            email = email,
            password = password
        )
    }
}