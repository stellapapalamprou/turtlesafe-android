package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel
import com.spapalamprou.turtlesafe.domain.repositories.NestRelocationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestRelocationUseCaseTest {
    private lateinit var useCase: NestRelocationUseCase
    private val repository: NestRelocationRepository = mockk()

    @Before
    fun setUp() {
        useCase = NestRelocationUseCase(repository)
    }

    @Test
    fun nestRelocation_submitted() {
        runTest {
            val nestRelocation = NestRelocationModel(
                oldNestCode = "N001",
                relocatedTo = "BoB",
                sector = "A",
                subsector = "A2",
                reasonForRelocation = "Inundation",
                newNestCode = "N002",
                depthTopEgg = 10.5,
                depthBottomNest = 35.7,
                distanceToSea = 50.0,
                numOfEggsTransplanted = 120,
                commentsOrRemarks = "Relocation successful"
            )

            val morningSurveyId = 1010L

            coEvery { repository.saveToDatabase(nestRelocation, morningSurveyId) } returns 123L
            val actual = useCase.submit(nestRelocation, morningSurveyId)
            coVerify { repository.saveToDatabase(nestRelocation, morningSurveyId) }
            assertEquals(123L, actual)
        }
    }
}