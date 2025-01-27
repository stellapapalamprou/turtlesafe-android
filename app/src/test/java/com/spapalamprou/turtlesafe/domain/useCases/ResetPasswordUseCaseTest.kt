package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.ResetPasswordModel
import com.spapalamprou.turtlesafe.domain.repositories.ResetPasswordRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ResetPasswordUseCaseTest {
    private lateinit var useCase: ResetPasswordUseCase
    private val repository: ResetPasswordRepository = mockk()

    @Before
    fun setUp() {
        useCase = ResetPasswordUseCase(repository)
    }

    @Test
    fun resetPassword_submitted() {
        runTest {
            val resetPassword = ResetPasswordModel(
                userId = "123",
                token = "q1w2e3r4t5y6",
                password = "newPassword123"
            )

            coEvery { repository.sendToServer(resetPassword) } returns Unit
            useCase.submit(resetPassword)
            coVerify { repository.sendToServer(resetPassword) }
        }
    }
}