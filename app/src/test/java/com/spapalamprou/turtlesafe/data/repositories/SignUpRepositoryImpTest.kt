package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.SignUpJsonResponse
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.domain.models.SignUpModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SignUpRepositoryImpTest {
    private lateinit var repository: SignUpRepositoryImp
    private val authenticationApi: AuthenticationApi = mockk()

    @Before
    fun setUp() {
        repository = SignUpRepositoryImp(authenticationApi)
    }

    @Test
    fun signUp_sendToServerTest() {
        runTest {
            val user = SignUpModel(
                firstName = "Mary",
                lastName = "Jane",
                email = "volunteer@gmail.com",
                password = "A123b456"
            )

            val response = SignUpJsonResponse(
                firstName = "Mary",
                lastName = "Jane",
                email = "volunteer@gmail.com"
            )

            coEvery {
                authenticationApi.createUser(user.asJson())
            } returns response

            repository.sendToServer(user)
            coVerify { authenticationApi.createUser(user.asJson()) }
        }
    }
}