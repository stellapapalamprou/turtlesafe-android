package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.HatchingDataModel
import com.spapalamprou.turtlesafe.domain.models.IncubationDataModel
import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel
import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel
import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel
import com.spapalamprou.turtlesafe.domain.models.NewNestModel
import com.spapalamprou.turtlesafe.domain.repositories.MorningSurveyRepository
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.UnknownHostException

class MorningSurveyUseCaseTest {
    private lateinit var useCase: MorningSurveyUseCase
    private val morningSurveyRepository: MorningSurveyRepository = mockk()
    private val newNestRepository: NewNestRepository = mockk()
    private val newNestUseCase: NewNestUseCase = mockk()
    private val nestIncubationUseCase: NestIncubationUseCase = mockk()
    private val nestRelocationUseCase: NestRelocationUseCase = mockk()
    private val nestHatchingUseCase: NestHatchingUseCase = mockk()
    private val nestExcavationUseCase: NestExcavationUseCase = mockk()


    @Before
    fun setUp() {
        useCase = MorningSurveyUseCase(
            morningSurveyRepository,
            newNestRepository,
            newNestUseCase,
            nestIncubationUseCase,
            nestRelocationUseCase,
            nestHatchingUseCase,
            nestExcavationUseCase
        )
    }

    @Test
    fun morningSurvey_submitted() {
        runTest {
            val newNest = NewNestModel(
                layingDate = "2024-09-14",
                area = "LAK",
                beach = "Valtaki",
                sector = "B",
                subsector = "B3",
                nestCode = "N200",
                trackType = "U-Track",
                gpsLatitude = 36.7783,
                gpsLongtitude = -119.4179,
                photoUri = null,
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                turtleTags = "T12345, T67890",
                emergenceOrEvent = "Predation",
                depthTopEgg = 15.0,
                distanceToSea = 25.5,
                commentsOrRemarks = "Human activity nearby"
            )
            val incubationData1 = IncubationDataModel(selectedIncubationEvent = "Predation")
            val incubationData2 = IncubationDataModel(selectedIncubationEvent = "Inundation")

            val incubation = NestIncubationModel(
                nestCode = "NEST123",
                nestLocation = "Hatchery",
                hatcheryCode = "HATCH001",
                incubationDataList = listOf(incubationData1, incubationData2),
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                commentsOrRemarks = "Nest under observation"
            )
            val hatchingData1 = HatchingDataModel(selectedHatchingEvent = "Predation Attempt")
            val hatchingData2 = HatchingDataModel(selectedHatchingEvent = "Emergence")

            val hatching = NestHatchingModel(
                nestCode = "NEST456",
                firstDayHatching = "2024-09-10",
                lastDayHatching = "2024-09-15",
                hatchingDataList = listOf(hatchingData1, hatchingData2),
                commentsOrRemarks = "Successful hatching"
            )
            val relocation = NestRelocationModel(
                oldNestCode = "OLDNEST001",
                relocatedTo = "BoB",
                sector = "A",
                subsector = "A2",
                reasonForRelocation = "High tide risk",
                newNestCode = "NEWNEST002",
                depthTopEgg = 0.2,
                depthBottomNest = 0.4,
                distanceToSea = 5.0,
                numOfEggsTransplanted = 12,
                commentsOrRemarks = "Relocation due to erosion risk"
            )
            val excavation = NestExcavationModel(
                nestCode = "NEST789",
                hatchedEggs = 85,
                pippedDeadHatchlings = 5,
                pippedLiveHatchlings = 3,
                noEmbryos = 2,
                deadEmbryos = 4,
                liveEmbryos = 1,
                deadHatchlingsNest = 6,
                liveHatchlingsNest = 7,
                commentsOrRemarks = "Excavation completed"
            )
            val morningSurvey = MorningSurveyModel(
                date = "2024-08-14",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                newNestList = listOf(newNest),
                nestIncubationList = listOf(incubation),
                nestRelocationList = listOf(relocation),
                nestHatchingList = listOf(hatching),
                nestExcavationList = listOf(excavation)
            )

            coEvery { morningSurveyRepository.saveToDatabase(morningSurvey) } returns 123L
            for (nest in morningSurvey.newNestList) {
                coEvery { newNestUseCase.submit(nest, 123L) } returns 1L
            }
            for (nest in morningSurvey.nestIncubationList) {
                coEvery { nestIncubationUseCase.submit(nest, 123L) } returns 2L
            }
            for (nest in morningSurvey.nestHatchingList) {
                coEvery { nestHatchingUseCase.submit(nest, 123L) } returns 3L
            }
            for (nest in morningSurvey.nestRelocationList) {
                coEvery { nestRelocationUseCase.submit(nest, 123L) } returns 4L
            }
            for (nest in morningSurvey.nestExcavationList) {
                coEvery { nestExcavationUseCase.submit(nest, 123L) } returns 5L
            }

            coEvery { morningSurveyRepository.sendToServer(morningSurvey) } returns Unit
            for (nest in morningSurvey.newNestList) {
                coEvery { newNestRepository.sendPhotoToServer(nest) } returns Unit
            }
            useCase.submit(morningSurvey)
            coVerify { morningSurveyRepository.saveToDatabase(morningSurvey) }
            for (nest in morningSurvey.newNestList) {
                coVerify { newNestUseCase.submit(nest, 123L) }
            }
            for (nest in morningSurvey.nestIncubationList) {
                coVerify { nestIncubationUseCase.submit(nest, 123L) }
            }
            for (nest in morningSurvey.nestHatchingList) {
                coVerify { nestHatchingUseCase.submit(nest, 123L) }
            }
            for (nest in morningSurvey.nestRelocationList) {
                coVerify { nestRelocationUseCase.submit(nest, 123L) }
            }
            for (nest in morningSurvey.nestExcavationList) {
                coVerify { nestExcavationUseCase.submit(nest, 123L) }
            }

            coVerify { morningSurveyRepository.sendToServer(morningSurvey) }
            for (nest in morningSurvey.newNestList) {
                coVerify { newNestRepository.sendPhotoToServer(nest) }
            }
            coVerify(exactly = 0) { morningSurveyRepository.startWorker(123L) }
        }
    }

    @Test
    fun morningSurvey_unknownHost() {
        runTest {
            val newNest = NewNestModel(
                layingDate = "2024-09-14",
                area = "LAK",
                beach = "Valtaki",
                sector = "B",
                subsector = "B3",
                nestCode = "N200",
                trackType = "U-Track",
                gpsLatitude = 36.7783,
                gpsLongtitude = -119.4179,
                photoUri = null,
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                turtleTags = "T12345, T67890",
                emergenceOrEvent = "Predation",
                depthTopEgg = 15.0,
                distanceToSea = 25.5,
                commentsOrRemarks = "Human activity nearby"
            )
            val morningSurvey = MorningSurveyModel(
                date = "2024-08-14",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                newNestList = listOf(newNest),
                nestIncubationList = listOf(),
                nestRelocationList = listOf(),
                nestHatchingList = listOf(),
                nestExcavationList = listOf()
            )

            coEvery { morningSurveyRepository.saveToDatabase(morningSurvey) } returns 123L
            for (nest in morningSurvey.newNestList) {
                coEvery { newNestUseCase.submit(nest, 123L) } returns 1L
            }
            coEvery { morningSurveyRepository.sendToServer(morningSurvey) } throws UnknownHostException()
            coEvery { morningSurveyRepository.startWorker(123L) } returns Unit
            useCase.submit(morningSurvey)
            coVerify { morningSurveyRepository.saveToDatabase(morningSurvey) }
            for (nest in morningSurvey.newNestList) {
                coVerify { newNestUseCase.submit(nest, 123L) }
            }
            coVerify { morningSurveyRepository.sendToServer(morningSurvey) }
            coVerify { morningSurveyRepository.startWorker(123L) }

            coVerify(exactly = 0) { newNestRepository.sendPhotoToServer(any()) }
            coVerify(exactly = 0) { nestIncubationUseCase.submit(any(), 123L) }
            coVerify(exactly = 0) { nestRelocationUseCase.submit(any(), 123L) }
            coVerify(exactly = 0) { nestHatchingUseCase.submit(any(), 123L) }
            coVerify(exactly = 0) { nestExcavationUseCase.submit(any(), 123L) }
        }
    }

    @Test
    fun morningSurvey_connectException() {
        runTest {
            val newNest = NewNestModel(
                layingDate = "2024-09-14",
                area = "LAK",
                beach = "Valtaki",
                sector = "B",
                subsector = "B3",
                nestCode = "N200",
                trackType = "U-Track",
                gpsLatitude = 36.7783,
                gpsLongtitude = -119.4179,
                photoUri = null,
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                turtleTags = "T12345, T67890",
                emergenceOrEvent = "Predation",
                depthTopEgg = 15.0,
                distanceToSea = 25.5,
                commentsOrRemarks = "Human activity nearby"
            )
            val morningSurvey = MorningSurveyModel(
                date = "2024-08-14",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                newNestList = listOf(newNest),
                nestIncubationList = listOf(),
                nestRelocationList = listOf(),
                nestHatchingList = listOf(),
                nestExcavationList = listOf()
            )

            coEvery { morningSurveyRepository.saveToDatabase(morningSurvey) } returns 123L
            for (nest in morningSurvey.newNestList) {
                coEvery { newNestUseCase.submit(nest, 123L) } returns 1L
            }
            coEvery { morningSurveyRepository.sendToServer(morningSurvey) } throws ConnectException()
            coEvery { morningSurveyRepository.startWorker(123L) } returns Unit
            useCase.submit(morningSurvey)
            coVerify { morningSurveyRepository.saveToDatabase(morningSurvey) }
            for (nest in morningSurvey.newNestList) {
                coVerify { newNestUseCase.submit(nest, 123L) }
            }
            coVerify { morningSurveyRepository.sendToServer(morningSurvey) }
            coVerify { morningSurveyRepository.startWorker(123L) }

            coVerify(exactly = 0) { newNestRepository.sendPhotoToServer(any()) }
            coVerify(exactly = 0) { nestIncubationUseCase.submit(any(), 123L) }
            coVerify(exactly = 0) { nestRelocationUseCase.submit(any(), 123L) }
            coVerify(exactly = 0) { nestHatchingUseCase.submit(any(), 123L) }
            coVerify(exactly = 0) { nestExcavationUseCase.submit(any(), 123L) }
        }
    }
}