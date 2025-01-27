package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.HatchingDataModel
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel
import com.spapalamprou.turtlesafe.domain.repositories.NestHatchingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestHatchingUseCaseTest {
    private lateinit var useCase: NestHatchingUseCase
    private val repository: NestHatchingRepository = mockk()

    @Before
    fun setUp() {
        useCase = NestHatchingUseCase(repository)
    }

    @Test
    fun nestHatching_submitted() {
        runTest {
            val hatchingEvent = HatchingDataModel(
                selectedHatchingEvent = "Inundation"
            )

            val hatching = NestHatchingModel(
                nestCode = "123",
                firstDayHatching = "2024-09-01",
                lastDayHatching = "2024-09-03",
                hatchingDataList = listOf(hatchingEvent),
                commentsOrRemarks = "Nest observed in good condition."
            )

            val morningSurveyId = 1010L

            coEvery { repository.saveToDatabase(hatching, morningSurveyId) } returns 123L
            val actual = useCase.submit(hatching, morningSurveyId)
            coVerify { repository.saveToDatabase(hatching, morningSurveyId) }
            assertEquals(123L, actual)
        }
    }
}