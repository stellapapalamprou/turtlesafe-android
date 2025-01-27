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

class NewNestApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: NewNestApi

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
            .create(NewNestApi::class.java)
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testNewNestApi_getNestCode() {
        runTest {
            val jsonResponse = """
                [
                    {
                        "nest_code": "2024B",
                        "laying_date": "2024-08-14",
                        "area": "LAK",
                        "beach": "Trinisa",
                        "sector": "H",
                        "subsector": "On1976",
                        "track_type": "A",
                        "gps_latitude": 37.4220936,
                        "gps_longtitude": -122.083922,
                        "protected_nest_switch": true,
                        "protection_measures": "T",
                        "turtle_tags": "",
                        "emergence_or_event": "V: Vandalism",
                        "depth_top_egg": 1.0,
                        "distance_to_sea": 2.0,
                        "comments_or_remarks": "",
                        "morning_survey_id": 10
                    },
                    {
                        "nest_code": "AC5644",
                        "laying_date": "2024-08-07",
                        "area": "LAK",
                        "beach": "Trinisa",
                        "sector": "H",
                        "subsector": "On1976",
                        "track_type": "C",
                        "gps_latitude": 37.4220936,
                        "gps_longtitude": -122.083922,
                        "protected_nest_switch": false,
                        "protection_measures": "",
                        "turtle_tags": "",
                        "emergence_or_event": "P: Predation",
                        "depth_top_egg": 121.0,
                        "distance_to_sea": 1.0,
                        "comments_or_remarks": "qqq",
                        "morning_survey_id": 14
                    }
                ]
            """.trimIndent()

            val response = MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)

            mockWebServer.enqueue(response)
            var apiResponse =
                api.getNestData(area = "LAK", beach = "Trinisa", sector = "H", subsector = "On1976")

            val expected = listOf(GetNewNestJsonResponse("2024B"), GetNewNestJsonResponse("AC5644"))
            assertEquals(expected, apiResponse)
        }
    }

    @Test
    fun testNewNestApi_500error() {
        runTest {
            val response = MockResponse()
                .setResponseCode(500)
            mockWebServer.enqueue(response)

            exception.expect(HttpException::class.java)
            api.getNestData(area = "LAK", beach = "Trinisa", sector = "H", subsector = "On1976")
        }
    }
}