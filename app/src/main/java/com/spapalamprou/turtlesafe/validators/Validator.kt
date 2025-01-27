package com.spapalamprou.turtlesafe.validators

import android.util.Patterns
import javax.inject.Inject

/**
 * A class for validating user input.
 */
class Validator @Inject constructor() {

    /**
     * Validates the provided email.
     *
     * @param email The email to be validated.
     * @return ValidationResult.Success if the email is valid, otherwise returns ValidationResult.EmailError.
     */
    fun validateEmail(
        email: String
    ): ValidationResult {
        if (email.isEmail()) {
            return ValidationResult.Success
        } else {
            return ValidationResult.EmailError
        }
    }

    /**
     * Validates the password provided.
     *
     * @param password The password to be validated.
     * @return ValidationResult.Success if the password meets the criteria, otherwise returns ValidationResult.PasswordError.
     */
    fun validatePassword(
        password: String
    ): ValidationResult {
        if (password.isPassword()) {
            return ValidationResult.Success
        } else {
            return ValidationResult.PasswordError
        }
    }

    /**
     * Validates that the provided text is not empty.
     *
     * @param text The text to be validated.
     * @return ValidationResult.Success if the text is non-empty, otherwise returns ValidationResult.NonEmptyError.
     */
    fun validateNonEmptyField(
        text: String
    ): ValidationResult {
        if (text.isNotEmpty()) {
            return ValidationResult.Success
        } else {
            return ValidationResult.NonEmptyError
        }
    }

    /**
     * Validates the provided name.
     *
     * @param name The name to be validated.
     * @return ValidationResult.Success if the name is valid, otherwise returns ValidationResult.NameError.
     */
    fun validateName(
        name: String
    ): ValidationResult {
        if (name.isName()) {
            return ValidationResult.Success
        } else {
            return ValidationResult.NameError
        }
    }

    /**
     * Validates that the text is a double.
     *
     * @param text The text to be validated.
     * @return ValidationResult.Success if the text is a double, otherwise returns ValidationResult.NumericTextError.
     */
    fun validateNumericText(
        text: String
    ): ValidationResult {
        if (text.isDouble()) {
            return ValidationResult.Success
        } else {
            return ValidationResult.NumericTextError
        }
    }

    /**
     * Validates that the provided text is an integer.
     *
     * @param text The text to be validated.
     * @return ValidationResult.Success if the text is an integer, otherwise returns ValidationResult.IntegerTextError.
     */
    fun validateIntegerText(
        text: String
    ): ValidationResult {
        if (text.isInt()) {
            return ValidationResult.Success
        } else {
            return ValidationResult.IntegerTextError
        }
    }
}

/**
 * Extension function to validate if the string is a valid email address.
 *
 * @return true if the string is a valid email address, false otherwise.
 */
fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Extension function to validate if the string meets password criteria.
 *
 * @return true if the string meets password criteria, false otherwise.
 */
fun String.isPassword(): Boolean {
    val hasLetter: Boolean = this.any { char -> char.isLetter() }
    val hasNumber: Boolean = this.any { char -> char.isDigit() }
    val hasValidLength: Boolean = this.length >= 8
    return hasLetter && hasNumber && hasValidLength
}

/**
 * Extension function to validate if the string is a valid name.
 *
 * @return true if the string is a valid name, false otherwise.
 */
fun String.isName(): Boolean {
    return (this.all { char -> char.isLetter() || char.isWhitespace() })
            && (this.any { char -> char.isLetter() })
}

/**
 * Extension function to validate if the text is a double.
 *
 * @return true if the text is a double, false otherwise.
 */
fun String.isDouble(): Boolean {
    return this.toDoubleOrNull() != null
}

/**
 * Extension function to validate if the text is an integer.
 *
 * @return true if the text is an integer, false otherwise.
 */
fun String.isInt(): Boolean {
    return this.toIntOrNull() != null
}

/**
 * Sealed class representing the result of a validation operation.
 *
 * @property message A message associated with the validation result.
 */
sealed class ValidationResult(val message: String) {
    object Success : ValidationResult(message = "")
    object EmailError : ValidationResult(message = "Invalid email")
    object PasswordError : ValidationResult(message = "Invalid password")
    object NonEmptyError : ValidationResult(message = "Field cannot be empty")
    object NameError : ValidationResult(message = "Invalid input")
    object NumericTextError : ValidationResult(message = "Input must be a number")
    object IntegerTextError : ValidationResult(message = "Input must be an integer")
}