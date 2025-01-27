package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing the sign up data.
 *
 * @property firstName The user's first name.
 * @property lastName The user's last name.
 * @property email The user's email address.
 * @property password The user's password.
 */
data class SignUpModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)