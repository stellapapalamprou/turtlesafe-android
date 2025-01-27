package com.spapalamprou.turtlesafe.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.spapalamprou.turtlesafe.ui.components.TextDivider
import org.junit.Rule
import org.junit.Test

class DividersTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testTextDivider_displaysText() {
        val text = "Event 1"

        rule.setContent {
            TextDivider(text = text)
        }
        rule.onNodeWithTag("dividerHasText").assertTextEquals(text)
    }
}