package com.spapalamprou.turtlesafe.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.spapalamprou.turtlesafe.ui.components.LabelSwitch
import org.junit.Rule
import org.junit.Test

class SwitchesTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testLabelSwitch_isSwitchedOn() {

        rule.setContent {
            var isSwitchOn by remember {
                mutableStateOf(true)
            }
            LabelSwitch(onCheckedChange = { input ->
                isSwitchOn = input
            }, text = "", switchChecked = isSwitchOn)
        }
        rule.onNodeWithTag("switch").assertIsOn()
        rule.onNodeWithTag("switch").performClick()
        rule.onNodeWithTag("switch").assertIsOff()
    }
}