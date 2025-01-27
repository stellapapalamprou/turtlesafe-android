package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.NewTagDataJsonResponse
import com.spapalamprou.turtlesafe.data.api.NightSurveyApi
import com.spapalamprou.turtlesafe.data.api.NightSurveyJsonResponse
import com.spapalamprou.turtlesafe.data.api.OldTagDataJsonResponse
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.database.dao.NightSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.data.workers.NightSurveyWorkerManager
import com.spapalamprou.turtlesafe.domain.models.NewTagDataModel
import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.models.OldTagDataModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NightSurveyRepositoryImpTest {
    private lateinit var repository: NightSurveyRepositoryImp
    private val dao: NightSurveyDao = mockk()
    private val api: NightSurveyApi = mockk()
    private val manager: NightSurveyWorkerManager = mockk()

    @Before
    fun setUp() {
        repository = NightSurveyRepositoryImp(dao, api, manager)
    }

    @Test
    fun nightSurvey_savedToDatabase() {
        runTest {
            val oldTagData = OldTagDataModel(
                selectedOldTagLocation = "Front Flipper Right",
                oldTagCode = "OT67890",
                isTurtleSafeSwitchChecked = false,
                tagNotes = "Tag in good condition"
            )

            val newTagData = NewTagDataModel(
                selectedNewTagLocation = "Rear Flipper Left",
                newTagCode = "NT54321",
                isTagSwitchChecked = true,
                selectedNewScarType = "Cut",
                newScarLocation = "Flipper edge"
            )
            val nightSurvey = NightSurveyModel(
                date = "2024-09-10",
                area = "LAK",
                beach = "Selinitsa",
                sector = "A",
                subsector = "A2",
                tagger = "Mary Jane",
                oldTagDataList = listOf(oldTagData),
                newTagDataList = listOf(newTagData),
                lostTagScars = "No scars observed",
                straightCarapaceLength = 85.0,
                straightCarapaceWidth = 65.5,
                curvedCarapaceLength = 90.0,
                curvedCarapaceWidth = 70.0,
                damageFlippersHead = "None",
                damageCarapace = "Minor scratches",
                nestEmergenceSwitch = true,
                nestCode = "N100",
                depthTopEgg = 15.5,
                depthBottomNests = 40.0,
                distanceToSea = 30.0,
                numEggsRelocated = 120,
                relocationComments = "Relocated due to high tide risk",
                numEggsExcavated = 118,
                startLaying = "02:15",
                startCover = "02:45",
                startCamouflage = "03:00",
                departNestSite = "03:15",
                turtleAtSea = "03:20",
                commentsRemarks = "The turtle showed no signs of distress."
            )

            coEvery {
                dao.insert(
                    nightSurveyEntity = nightSurvey.asEntity(),
                    oldTagEntities = nightSurvey.oldTagDataList.map { tag -> tag.asEntity() },
                    newTagEntities = nightSurvey.newTagDataList.map { tag -> tag.asEntity() }
                )
            } returns 123L

            val actual = repository.saveToDatabase(nightSurvey)
            coVerify {
                dao.insert(
                    nightSurveyEntity = nightSurvey.asEntity(),
                    oldTagEntities = nightSurvey.oldTagDataList.map { tag -> tag.asEntity() },
                    newTagEntities = nightSurvey.newTagDataList.map { tag -> tag.asEntity() }
                )
            }
            assertEquals(123L, actual)
        }
    }

    @Test
    fun nightSurvey_sentToServer() {
        runTest {
            val oldTagData = OldTagDataModel(
                selectedOldTagLocation = "Front Flipper Right",
                oldTagCode = "OT67890",
                isTurtleSafeSwitchChecked = false,
                tagNotes = "Tag in good condition"
            )

            val newTagData = NewTagDataModel(
                selectedNewTagLocation = "Rear Flipper Left",
                newTagCode = "NT54321",
                isTagSwitchChecked = true,
                selectedNewScarType = "Cut",
                newScarLocation = "Flipper edge"
            )
            val nightSurvey = NightSurveyModel(
                date = "10/09/2024",
                area = "LAK",
                beach = "Selinitsa",
                sector = "A",
                subsector = "A2",
                tagger = "Mary Jane",
                oldTagDataList = listOf(oldTagData),
                newTagDataList = listOf(newTagData),
                lostTagScars = "No scars observed",
                straightCarapaceLength = 85.0,
                straightCarapaceWidth = 65.5,
                curvedCarapaceLength = 90.0,
                curvedCarapaceWidth = 70.0,
                damageFlippersHead = "None",
                damageCarapace = "Minor scratches",
                nestEmergenceSwitch = true,
                nestCode = "N100",
                depthTopEgg = 15.5,
                depthBottomNests = 40.0,
                distanceToSea = 30.0,
                numEggsRelocated = 120,
                relocationComments = "Relocated due to high tide risk",
                numEggsExcavated = 118,
                startLaying = "02:15",
                startCover = "02:45",
                startCamouflage = "03:00",
                departNestSite = "03:15",
                turtleAtSea = "03:20",
                commentsRemarks = "The turtle showed no signs of distress."
            )

            val oldTagDataResponse = OldTagDataJsonResponse(
                id = 1,
                selectedOldTagLocation = "Front Flipper Right",
                oldTagCode = "OT67890",
                isTurtleSafeSwitchChecked = false,
                tagNotes = "Tag in good condition"
            )

            val newTagDataResponse = NewTagDataJsonResponse(
                id = 1,
                selectedNewTagLocation = "Rear Flipper Left",
                newTagCode = "NT54321",
                isTagSwitchChecked = true,
                selectedNewScarType = "Cut",
                newScarLocation = "Flipper edge"
            )
            val nightSurveyResponse = NightSurveyJsonResponse(
                id = 1,
                date = "10/09/2024",
                area = "LAK",
                beach = "Selinitsa",
                sector = "A",
                subsector = "A2",
                tagger = "Mary Jane",
                oldTagDataList = listOf(oldTagDataResponse),
                newTagDataList = listOf(newTagDataResponse),
                lostTagScars = "No scars observed",
                straightCarapaceLength = 85.0,
                straightCarapaceWidth = 65.5,
                curvedCarapaceLength = 90.0,
                curvedCarapaceWidth = 70.0,
                damageFlippersHead = "None",
                damageCarapace = "Minor scratches",
                nestEmergenceSwitch = true,
                nestCode = "N100",
                depthTopEgg = 15.5,
                depthBottomNests = 40.0,
                distanceToSea = 30.0,
                numEggsRelocated = 120,
                relocationComments = "Relocated due to high tide risk",
                numEggsExcavated = 118,
                startLaying = "02:15",
                startCover = "02:45",
                startCamouflage = "03:00",
                departNestSite = "03:15",
                turtleAtSea = "03:20",
                commentsRemarks = "The turtle showed no signs of distress."
            )

            coEvery { api.createNightSurvey(nightSurvey.asJson()) } returns nightSurveyResponse
            repository.sendToServer(nightSurvey)
            coVerify { api.createNightSurvey(nightSurvey.asJson()) }
        }
    }


    @Test
    fun nightSurvey_testWorker() {
        runTest {
            val id = 123L

            coEvery { manager.startWorker(id) } returns Unit
            repository.startWorker(id)
            coVerify { manager.startWorker(id) }
        }
    }
}