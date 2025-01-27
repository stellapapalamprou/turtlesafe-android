package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestExcavationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestExcavationUseCaseTest {
    private lateinit var useCase: NestExcavationUseCase
    private val repository: NestExcavationRepository = mockk()

    @Before
    fun setUp() {
        useCase = NestExcavationUseCase(repository)
    }

    @Test
    fun nestExcavation_submitted() {
        runTest {
            val nestExcavation = NestExcavationModel(
                nestCode = "123",
                hatchedEggs = 50,
                pippedDeadHatchlings = 2,
                pippedLiveHatchlings = 5,
                noEmbryos = 3,
                deadEmbryos = 4,
                liveEmbryos = 6,
                deadHatchlingsNest = 1,
                liveHatchlingsNest = 8,
                commentsOrRemarks = "Nest observed in good condition."
            )

            val morningSurveyId = 1010L

            coEvery { repository.saveToDatabase(nestExcavation, morningSurveyId) } returns 123L
            val actual = useCase.submit(nestExcavation, morningSurveyId)
            coVerify { repository.saveToDatabase(nestExcavation, morningSurveyId) }
            assertEquals(123L, actual)
        }
    }

}