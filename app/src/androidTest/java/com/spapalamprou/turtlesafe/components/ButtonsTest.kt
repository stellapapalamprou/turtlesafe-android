package com.spapalamprou.turtlesafe.components

import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.spapalamprou.turtlesafe.ui.components.LabelRoundButton
import org.junit.Rule
import org.junit.Test

class ButtonsTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testLabelRoundButton_displaysTexts() {
        val text = "Add Event"
        val subtext = "Add all events"

        rule.setContent {
            LabelRoundButton(
                onClick = {},
                text = text,
                subText = subtext
            )
        }

        rule.onNodeWithTag("labelRoundButtonText").assertTextEquals(text)
        rule.onNodeWithTag("labelRoundButtonSubtext").assertTextEquals(subtext)
    }

    @Test
    fun testLabelRoundButton_displaysNoSubtext() {
        val text = "Add Event"
        val subtext = ""

        rule.setContent {
            LabelRoundButton(
                onClick = {},
                text = text,
                subText = subtext
            )
        }

        rule.onNodeWithTag("labelRoundButtonText").assertTextEquals(text)
        rule.onNodeWithTag("labelRoundButtonSubtext").assertIsNotDisplayed()
    }
}