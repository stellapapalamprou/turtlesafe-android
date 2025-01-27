package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.NewNestModel
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewNestUseCaseTest {
    private lateinit var useCase: NewNestUseCase
    private val repository: NewNestRepository = mockk()

    @Before
    fun setUp() {
        useCase = NewNestUseCase(repository)
    }

    @Test
    fun newNest_submitted() {
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

            val morningSurveyId = 1L

            coEvery { repository.saveToDatabase(newNest, morningSurveyId) } returns 123L
            val actual = useCase.submit(newNest, morningSurveyId)
            coVerify { repository.saveToDatabase(newNest, morningSurveyId) }
            assertEquals(123L, actual)
        }
    }
}