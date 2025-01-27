package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.models.SignUpModel
import com.spapalamprou.turtlesafe.domain.repositories.SignUpRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SignUpUseCaseTest {
    private lateinit var useCase: SignUpUseCase
    private val repository: SignUpRepository = mockk()

    @Before
    fun setUp() {
        useCase = SignUpUseCase(repository)
    }

    @Test
    fun signUp_submitted() {
        runTest {
            val signUp = SignUpModel(
                firstName = "Mary",
                lastName = "Jane",
                email = "volunteer@gmail.com",
                password = "A123b456"
            )

            coEvery { repository.sendToServer(signUp) } returns Unit
            useCase.submit(signUp)
            coVerify { repository.sendToServer(signUp) }
        }
    }
}