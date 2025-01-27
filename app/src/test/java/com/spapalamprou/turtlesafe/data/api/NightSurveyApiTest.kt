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

class NightSurveyApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: NightSurveyApi

    @get:Rule
    var exception: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)

        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(NightSurveyApi::class.java)
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testNightSurveyApi_postNightSurvey() {
        runTest {
            val jsonFromServer = """
                {
                    "id": 4,
                    "old_tags": [
                        {
                            "id": 2,
                            "selected_old_tag_location": "left_flipper",
                            "old_tag_code": "A12345",
                            "is_turtlesafe_tag": true,
                            "tag_notes": "Tag is slightly worn but legible.",
                            "night_survey_id": 4
                        },
                        {
                            "id": 3,
                            "selected_old_tag_location": "right_flipper",
                            "old_tag_code": "B67890",
                            "is_turtlesafe_tag": false,
                            "tag_notes": "Tag is in good condition.",
                            "night_survey_id": 4
                        }
                    ],
                    "new_tags": [
                        {
                            "id": 1,
                            "selected_new_tag_location": "left_flipper",
                            "new_tag_code": "C54321",
                            "is_tagging_successful": true,
                            "selected_new_scar_type": "healed_scar",
                            "new_scar_location": "flipper",
                            "night_survey_id": 4
                        },
                        {
                            "id": 2,
                            "selected_new_tag_location": "right_flipper",
                            "new_tag_code": "D98765",
                            "is_tagging_successful": false,
                            "selected_new_scar_type": "fresh_scar",
                            "new_scar_location": "neck",
                            "night_survey_id": 4
                        }
                    ],
                    "date": "2023-09-12",
                    "area": "Some Area",
                    "beach": "Some Beach",
                    "sector": "Some Sector",
                    "subsector": "Some Subsector",
                    "tagger": "Tagger Name",
                    "lost_tag_scars": "Some description",
                    "straight_carapace_length": "82.5",
                    "straight_carapace_width": 65.2,
                    "curved_carapace_length": 85.3,
                    "curved_carapace_width": 67.0,
                    "damage_flippers_head": "None",
                    "damage_carapace": "None",
                    "nest_emergence_switch": true,
                    "nest_code": "N1234",
                    "depth_top_egg": 30.5,
                    "depth_bottom_nests": 55.0,
                    "distance_to_sea": 10.0,
                    "number_of_eggs_relocated": 100,
                    "relocation_comments": "Relocated due to high tide",
                    "number_of_eggs_excavated": 85,
                    "start_laying": "22:00:00",
                    "start_cover": "22:30:00",
                    "start_camouflage": "22:45:00",
                    "depart_nest_site": "23:00:00",
                    "turtle_at_sea": "23:15:00",
                    "comments_remarks": "Turtle appeared healthy.",
                    "user": 1
                }
            """.trimIndent()

            val mockServerResponse = MockResponse()
                .setResponseCode(201)
                .setBody(jsonFromServer)

            mockWebServer.enqueue(mockServerResponse)

            val jsonBody = NightSurveyJson(
                date = "2023-09-12",
                area = "Some Area",
                beach = "Some Beach",
                sector = "Some Sector",
                subsector = "Some Subsector",
                tagger = "Tagger Name",
                oldTagDataList = listOf(
                    OldTagDataJson(
                        selectedOldTagLocation = "left_flipper",
                        oldTagCode = "A12345",
                        isTurtleSafeSwitchChecked = true,
                        tagNotes = "Tag is slightly worn but legible."
                    ),
                    OldTagDataJson(
                        selectedOldTagLocation = "right_flipper",
                        oldTagCode = "B67890",
                        isTurtleSafeSwitchChecked = false,
                        tagNotes = "Tag is in good condition."
                    )
                ),
                newTagDataList = listOf(
                    NewTagDataJson(
                        selectedNewTagLocation = "left_flipper",
                        newTagCode = "C54321",
                        isTagSwitchChecked = true,
                        selectedNewScarType = "healed_scar",
                        newScarLocation = "flipper"
                    ),
                    NewTagDataJson(
                        selectedNewTagLocation = "right_flipper",
                        newTagCode = "D98765",
                        isTagSwitchChecked = false,
                        selectedNewScarType = "fresh_scar",
                        newScarLocation = "neck"
                    )
                ),
                lostTagScars = "Some description",
                straightCarapaceLength = 82.5,
                straightCarapaceWidth = 65.2,
                curvedCarapaceLength = 85.3,
                curvedCarapaceWidth = 67.0,
                damageFlippersHead = "None",
                damageCarapace = "None",
                nestEmergenceSwitch = true,
                nestCode = "N1234",
                depthTopEgg = 30.5,
                depthBottomNests = 55.0,
                distanceToSea = 10.0,
                numEggsRelocated = 100,
                relocationComments = "Relocated due to high tide",
                numEggsExcavated = 85,
                startLaying = "22:00",
                startCover = "22:30",
                startCamouflage = "22:45",
                departNestSite = "23:00",
                turtleAtSea = "23:15",
                commentsRemarks = "Turtle appeared healthy."
            )

            var apiDeserializedResponse = api.createNightSurvey(jsonBody)
            val expected = NightSurveyJsonResponse(
                id = 4,
                date = "2023-09-12",
                area = "Some Area",
                beach = "Some Beach",
                sector = "Some Sector",
                subsector = "Some Subsector",
                tagger = "Tagger Name",
                oldTagDataList = listOf(
                    OldTagDataJsonResponse(
                        id = 2,
                        selectedOldTagLocation = "left_flipper",
                        oldTagCode = "A12345",
                        isTurtleSafeSwitchChecked = true,
                        tagNotes = "Tag is slightly worn but legible."
                    ),
                    OldTagDataJsonResponse(
                        id = 3,
                        selectedOldTagLocation = "right_flipper",
                        oldTagCode = "B67890",
                        isTurtleSafeSwitchChecked = false,
                        tagNotes = "Tag is in good condition."
                    )
                ),
                newTagDataList = listOf(
                    NewTagDataJsonResponse(
                        id = 1,
                        selectedNewTagLocation = "left_flipper",
                        newTagCode = "C54321",
                        isTagSwitchChecked = true,
                        selectedNewScarType = "healed_scar",
                        newScarLocation = "flipper"
                    ),
                    NewTagDataJsonResponse(
                        id = 2,
                        selectedNewTagLocation = "right_flipper",
                        newTagCode = "D98765",
                        isTagSwitchChecked = false,
                        selectedNewScarType = "fresh_scar",
                        newScarLocation = "neck"
                    )
                ),
                lostTagScars = "Some description",
                straightCarapaceLength = 82.5,
                straightCarapaceWidth = 65.2,
                curvedCarapaceLength = 85.3,
                curvedCarapaceWidth = 67.0,
                damageFlippersHead = "None",
                damageCarapace = "None",
                nestEmergenceSwitch = true,
                nestCode = "N1234",
                depthTopEgg = 30.5,
                depthBottomNests = 55.0,
                distanceToSea = 10.0,
                numEggsRelocated = 100,
                relocationComments = "Relocated due to high tide",
                numEggsExcavated = 85,
                startLaying = "22:00:00",
                startCover = "22:30:00",
                startCamouflage = "22:45:00",
                departNestSite = "23:00:00",
                turtleAtSea = "23:15:00",
                commentsRemarks = "Turtle appeared healthy."
            )
            assertEquals(expected, apiDeserializedResponse)
        }
    }

    @Test
    fun testNightSurveyApi_500error() {
        runTest {
            val response = MockResponse()
                .setResponseCode(500)
            mockWebServer.enqueue(response)

            exception.expect(HttpException::class.java)
            api.createNightSurvey(
                NightSurveyJson(
                    date = "2023-09-12",
                    area = "Some Area",
                    beach = "Some Beach",
                    sector = "Some Sector",
                    subsector = "Some Subsector",
                    tagger = "Tagger Name",
                    oldTagDataList = listOf(
                        OldTagDataJson(
                            selectedOldTagLocation = "left_flipper",
                            oldTagCode = "A12345",
                            isTurtleSafeSwitchChecked = true,
                            tagNotes = "Tag is slightly worn but legible."
                        ),
                        OldTagDataJson(
                            selectedOldTagLocation = "right_flipper",
                            oldTagCode = "B67890",
                            isTurtleSafeSwitchChecked = false,
                            tagNotes = "Tag is in good condition."
                        )
                    ),
                    newTagDataList = listOf(
                        NewTagDataJson(
                            selectedNewTagLocation = "left_flipper",
                            newTagCode = "C54321",
                            isTagSwitchChecked = true,
                            selectedNewScarType = "healed_scar",
                            newScarLocation = "flipper"
                        ),
                        NewTagDataJson(
                            selectedNewTagLocation = "right_flipper",
                            newTagCode = "D98765",
                            isTagSwitchChecked = false,
                            selectedNewScarType = "fresh_scar",
                            newScarLocation = "neck"
                        )
                    ),
                    lostTagScars = "Some description",
                    straightCarapaceLength = 82.5,
                    straightCarapaceWidth = 65.2,
                    curvedCarapaceLength = 85.3,
                    curvedCarapaceWidth = 67.0,
                    damageFlippersHead = "None",
                    damageCarapace = "None",
                    nestEmergenceSwitch = true,
                    nestCode = "N1234",
                    depthTopEgg = 30.5,
                    depthBottomNests = 55.0,
                    distanceToSea = 10.0,
                    numEggsRelocated = 100,
                    relocationComments = "Relocated due to high tide",
                    numEggsExcavated = 85,
                    startLaying = "22:00",
                    startCover = "22:30",
                    startCamouflage = "22:45",
                    departNestSite = "23:00",
                    turtleAtSea = "23:15",
                    commentsRemarks = "Turtle appeared healthy."
                )
            )
        }
    }
}