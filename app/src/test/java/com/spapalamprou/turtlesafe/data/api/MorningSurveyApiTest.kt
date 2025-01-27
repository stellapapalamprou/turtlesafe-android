package com.spapalamprou.turtlesafe.data.api

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MorningSurveyApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: MorningSurveyApi

    @get:Rule
    var exception: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)

        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(MorningSurveyApi::class.java)
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testMorningSurveyApi_postMorningSurvey() {
        runTest {
            val jsonFromServer = """
                {
                    "id": 24,
                    "date": "2024-08-14",
                    "time": "07:30:00",
                    "area": "Area 51",
                    "beach": "Trinisa Beach",
                    "nesting_attempt_switch": true,
                    "number_of_attempts": 2,
                    "comments_or_remarks": "Survey conducted smoothly with some unusual findings.",
                    "user": 1
                }
            """.trimIndent()

            val mockServerResponse = MockResponse()
                .setResponseCode(201)
                .setBody(jsonFromServer)

            mockWebServer.enqueue(mockServerResponse)

            val jsonBody = MorningSurveyJson(
                date = "2024-08-14",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                newNestEntries = listOf(),
                incubationEntries = listOf(),
                relocationEntries = listOf(),
                hatchingEntries = listOf(),
                excavationEntries = listOf()
            )
            val apiDeserializedResponse = api.createMorningSurvey(jsonBody)
            val expected = MorningSurveyJsonResponse(
                id = 24,
                date = "2024-08-14",
                time = "07:30:00",
                area = "Area 51",
                beach = "Trinisa Beach",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
            )
            assertEquals(expected, apiDeserializedResponse)
        }
    }

    @Test
    fun testMorningSurveyApi_500error() {
        runTest {
            val response = MockResponse()
                .setResponseCode(500)
            mockWebServer.enqueue(response)

            exception.expect(HttpException::class.java)
            api.createMorningSurvey(
                MorningSurveyJson(
                    date = "2024-08-14",
                    time = "07:30:00",
                    area = "Area 51",
                    beach = "Trinisa Beach",
                    nestingAttemptSwitch = true,
                    numberOfAttempts = 2,
                    commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                    newNestEntries = listOf(),
                    incubationEntries = listOf(),
                    relocationEntries = listOf(),
                    hatchingEntries = listOf(),
                    excavationEntries = listOf()
                )
            )
        }
    }
}