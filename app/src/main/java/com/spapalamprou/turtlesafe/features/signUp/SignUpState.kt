package com.spapalamprou.turtlesafe.features.signUp

import com.spapalamprou.turtlesafe.domain.models.LoginModel
import com.spapalamprou.turtlesafe.domain.models.SignUpModel

/**
 * Data class representing the state of the sign up screen.
 *
 * @property firstName The first name entered by the user.
 * @property invalidFirstNameMessage A message indicating any validation error related to the first name.
 * @property lastName The last name entered by the user.
 * @property invalidLastNameMessage A message indicating any validation error related to the last name.
 * @property email The email address entered by the user.
 * @property invalidEmailMessage A message indicating any validation error related to the email address.
 * @property password The password entered by the user.
 * @property invalidPasswordMessage A message indicating any validation error related to the password.
 * @property exception Any exception that might be thrown.
 */
data class SignUpState(
    val firstName: String = "",
    val invalidFirstNameMessage: String = "",
    val lastName: String = "",
    val invalidLastNameMessage: String = "",
    val email: String = "",
    val invalidEmailMessage: String = "",
    val password: String = "",
    val invalidPasswordMessage: String = "",
    val exception: Exception? = null
) {

    /**
     * Transforms the current state into a SignUpModel.
     *
     * @return A SignUpModel containing the user's first name, last name, email, and password.
     */
    fun transformData(): SignUpModel {
        return SignUpModel(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
    }

    /**
     * Transforms the current state into a LoginModel.
     *
     * @return A LoginModel containing the user's email and password.
     */
    fun transformDataToLogin(): LoginModel {
        return LoginModel(
            email = email,
            password = password
        )
    }
}