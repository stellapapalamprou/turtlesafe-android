package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.ForgotPasswordModel
import com.spapalamprou.turtlesafe.domain.repositories.ForgotPasswordRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ForgotPasswordUseCaseTest {
    private lateinit var useCase: ForgotPasswordUseCase
    private val repository: ForgotPasswordRepository = mockk()

    @Before
    fun setUp() {
        useCase = ForgotPasswordUseCase(repository)
    }

    @Test
    fun forgotPassword_submitted() {
        runTest {
            val forgotPassword = ForgotPasswordModel(
                email = "newuser@gmail.com"
            )

            coEvery { repository.sendToServer(forgotPassword) } returns Unit
            useCase.submit(forgotPassword)
            coVerify { repository.sendToServer(forgotPassword) }
        }
    }
}