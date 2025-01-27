package com.spapalamprou.turtlesafe.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.PasswordTextField
import org.junit.Rule
import org.junit.Test

class TextFieldsTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun passwordTextField_Hidden() {
        val password = "London"
        rule.setContent {
            PasswordTextField(password = password, onValueChange = {})
        }
        rule.onNodeWithTag("passwordTextfield", useUnmergedTree = true)
            .assertTextEquals("••••••")
    }

    @Test
    fun defaultTextField_ErrorShown() {
        val errorMessage = "Not a number"
        rule.setContent {
            DefaultTextField(
                text = "tall",
                onValueChange = {},
                label = "Height",
                onIconClick = {},
                errorMessage = errorMessage
            )
        }
        rule.onNodeWithTag("supportingText", useUnmergedTree = true)
            .assertTextEquals(errorMessage)
    }

    @Test
    fun defaultTextField_IconShown() {

        rule.setContent {
            DefaultTextField(
                text = "tall",
                onValueChange = {},
                label = "Height",
                onIconClick = {}
            )
        }
        rule.onNodeWithTag("defaultTextfieldIcon", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun defaultTextField_IconNotShownForReadOnly() {

        rule.setContent {
            DefaultTextField(
                text = "tall",
                onValueChange = {},
                label = "Height",
                onIconClick = {},
                readOnly = true
            )
        }
        rule.onNodeWithTag("defaultTextfieldIcon", useUnmergedTree = true)
            .assertIsNotDisplayed()
    }
}