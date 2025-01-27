package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetNestCodeUseCaseTest {
    private lateinit var useCase: GetNestCodeUseCase
    private val repository: NewNestRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetNestCodeUseCase(repository)
    }

    @Test
    fun nestCodeRetrieved() {
        runTest {
            val area = "LAK"
            val beach = "Valtaki"
            val sector = "A"
            val subsector = "A2"
            val nestCodes = listOf("A123", "B456")

            coEvery { repository.getFromServer(area, beach, sector, subsector) } returns nestCodes
            val actual = useCase.getNestCode(area, beach, sector, subsector)
            coVerify { repository.getFromServer(area, beach, sector, subsector) }
            assertEquals(nestCodes, actual)
        }
    }
}