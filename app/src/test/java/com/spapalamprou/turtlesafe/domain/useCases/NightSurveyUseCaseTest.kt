package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NewTagDataModel
import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.models.OldTagDataModel
import com.spapalamprou.turtlesafe.domain.repositories.NightSurveyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.UnknownHostException

class NightSurveyUseCaseTest {
    private lateinit var useCase: NightSurveyUseCase
    private val repository: NightSurveyRepository = mockk()

    @Before
    fun setUp() {
        useCase = NightSurveyUseCase(repository)
    }

    @Test
    fun nightSurvey_submitted() {
        runTest {
            val oldTagData = OldTagDataModel(
                selectedOldTagLocation = "Front Left Flipper",
                oldTagCode = "TAG1234",
                isTurtleSafeSwitchChecked = true,
                tagNotes = "Tag needs replacement"
            )
            val newTagData = NewTagDataModel(
                selectedNewTagLocation = "Front Right Flipper",
                newTagCode = "TAG6789",
                isTagSwitchChecked = true,
                selectedNewScarType = "Hole",
                newScarLocation = "Front Left Flipper"
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

            coEvery { repository.saveToDatabase(nightSurvey) } returns 123L
            coEvery { repository.sendToServer(nightSurvey) } returns Unit
            useCase.submit(nightSurvey)
            coVerify { repository.saveToDatabase(nightSurvey) }
            coVerify { repository.sendToServer(nightSurvey) }
            coVerify(exactly = 0) { repository.startWorker(123L) }
        }
    }

    @Test
    fun nightSurvey_unknownHost() {
        runTest {
            val nightSurvey = NightSurveyModel(
                date = "10/09/2024",
                area = "LAK",
                beach = "Selinitsa",
                sector = "A",
                subsector = "A2",
                tagger = "Mary Jane",
                oldTagDataList = listOf(),
                newTagDataList = listOf(),
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

            coEvery { repository.saveToDatabase(nightSurvey) } returns 123L
            coEvery { repository.sendToServer(nightSurvey) } throws UnknownHostException()
            coEvery { repository.startWorker(123L) } returns Unit
            useCase.submit(nightSurvey)
            coVerify { repository.saveToDatabase(nightSurvey) }
            coVerify { repository.sendToServer(nightSurvey) }
            coVerify { repository.startWorker(123L) }
        }
    }

    @Test
    fun nightSurvey_connectException() {
        runTest {
            val nightSurvey = NightSurveyModel(
                date = "10/09/2024",
                area = "LAK",
                beach = "Selinitsa",
                sector = "A",
                subsector = "A2",
                tagger = "Mary Jane",
                oldTagDataList = listOf(),
                newTagDataList = listOf(),
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

            coEvery { repository.saveToDatabase(nightSurvey) } returns 123L
            coEvery { repository.sendToServer(nightSurvey) } throws ConnectException()
            coEvery { repository.startWorker(123L) } returns Unit
            useCase.submit(nightSurvey)
            coVerify { repository.saveToDatabase(nightSurvey) }
            coVerify { repository.sendToServer(nightSurvey) }
            coVerify { repository.startWorker(123L) }
        }
    }
}