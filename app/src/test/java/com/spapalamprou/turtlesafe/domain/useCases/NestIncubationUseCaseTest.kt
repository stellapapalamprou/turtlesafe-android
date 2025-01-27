package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.IncubationDataModel
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestIncubationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestIncubationUseCaseTest {
    private lateinit var useCase: NestIncubationUseCase
    private val repository: NestIncubationRepository = mockk()

    @Before
    fun setUp() {
        useCase = NestIncubationUseCase(repository)
    }

    @Test
    fun nestIncubation_submitted() {
        runTest {
            val incubationEvent = IncubationDataModel(
                selectedIncubationEvent = "Predation"
            )

            val incubation = NestIncubationModel(
                nestCode = "123",
                nestLocation = "Hatchery",
                hatcheryCode = "H456",
                incubationDataList = listOf(incubationEvent),
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                commentsOrRemarks = "Normal incubation"
            )

            val morningSurveyId = 1010L

            coEvery { repository.saveToDatabase(incubation, morningSurveyId) } returns 123L
            val actual = useCase.submit(incubation, morningSurveyId)
            coVerify { repository.saveToDatabase(incubation, morningSurveyId) }
            assertEquals(123L, actual)
        }
    }
}