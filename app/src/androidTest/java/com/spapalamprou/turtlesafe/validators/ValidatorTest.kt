package com.spapalamprou.turtlesafe.validators

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatorTest {
    val validator: Validator = Validator()

    @Test
    fun email_isValid() {
        val email = "user@gmail.com"
        val result = validator.validateEmail(email)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun email_isNotValid() {
        val email = "user.com"
        val result = validator.validateEmail(email)
        assertEquals(ValidationResult.EmailError, result)
    }
}