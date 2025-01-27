package com.spapalamprou.turtlesafe.endToEnd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.spapalamprou.turtlesafe.MainActivity
import com.spapalamprou.turtlesafe.data.storage.TokenStorage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginToHomeScreen {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var server: MockWebServer

    @Inject
    lateinit var tokenStorage: TokenStorage

    @Before
    fun setUp() {
        hiltRule.inject()
        server = MockWebServer()
        server.start(12345)
    }

    @After
    fun shutDown() {
        server.shutdown()
        tokenStorage.deleteAll()
    }

    @Test
    fun login() {
        // Landing Screen
        composeRule.onNodeWithTag("landingScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("loginButton").performClick()

        // Login Screen
        composeRule.onNodeWithTag("loginScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("email").performTextInput("user@gmail.com")
        composeRule.onNodeWithTag("password").performTextInput("A!23b456")

        val response = MockResponse()
            .setBody(
                """
                {
                    "refresh": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTc1ODA0MDc5MCwiaWF0I",
                    "access": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzI2NTA4MzkwLCJpYXQiOj"
                }
            """.trimIndent()
            )
            .setResponseCode(200)
        server.enqueue(response)

        composeRule.onNodeWithTag("submitButton").performClick()

        // Home Screen
        composeRule.onNodeWithTag("homeScreen").assertIsDisplayed()
    }
}