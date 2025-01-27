package com.spapalamprou.turtlesafe.validators

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatorTest {
    val validator: Validator = Validator()

    @Test
    fun password_isValid() {
        val password = "London!23"
        val result = validator.validatePassword(password)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun password_isInvalid() {
        val password = "Lon"
        val result = validator.validatePassword(password)
        assertEquals(ValidationResult.PasswordError, result)
    }

    @Test
    fun nonEmptyField_isTrue() {
        val text = "city"
        val result = validator.validateNonEmptyField(text)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun nonEmptyField_isFalse() {
        val text = ""
        val result = validator.validateNonEmptyField(text)
        assertEquals(ValidationResult.NonEmptyError, result)
    }

    @Test
    fun name_isName() {
        val name = "S"
        val result = validator.validateName(name)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun name_isNotName() {
        val name = "1"
        val result = validator.validateName(name)
        assertEquals(ValidationResult.NameError, result)
    }

    @Test
    fun text_isNumeric() {
        val text = "1.1"
        val result = validator.validateNumericText(text)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun text_isNotNumeric() {
        val text = "a"
        val result = validator.validateNumericText(text)
        assertEquals(ValidationResult.NumericTextError, result)
    }

    @Test
    fun text_isInteger() {
        val text = "2"
        val result = validator.validateIntegerText(text)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun text_isNotInteger() {
        val text = "2.2"
        val result = validator.validateIntegerText(text)
        assertEquals(ValidationResult.IntegerTextError, result)
    }
}