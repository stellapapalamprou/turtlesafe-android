package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.LoginJsonResponse
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.api.asModel
import com.spapalamprou.turtlesafe.data.storage.TokenStorage
import com.spapalamprou.turtlesafe.domain.models.LoginModel
import com.spapalamprou.turtlesafe.domain.models.TokenModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginRepositoryImpTest {
    private lateinit var repository: LoginRepositoryImp
    private val authenticationApi: AuthenticationApi = mockk()
    private val tokenStorage: TokenStorage = mockk()

    @Before
    fun setUp() {
        repository = LoginRepositoryImp(authenticationApi, tokenStorage)
    }

    @Test
    fun tokenStorage_tokensSaved() {
        runTest {
            coEvery {
                tokenStorage.getAccessToken()
            } returns "a123"
            coEvery {
                tokenStorage.getRefreshToken()
            } returns "b456"

            val actual = repository.areTokensSaved()
            assertEquals(true, actual)
        }
    }

    @Test
    fun tokenStorage_tokensNotSaved() {
        runTest {
            coEvery {
                tokenStorage.getAccessToken()
            } returns null
            coEvery {
                tokenStorage.getRefreshToken()
            } returns null

            val actual = repository.areTokensSaved()
            assertEquals(false, actual)
        }
    }

    @Test
    fun tokenStorage_refreshNotSaved() {
        runTest {
            coEvery {
                tokenStorage.getAccessToken()
            } returns "a123"
            coEvery {
                tokenStorage.getRefreshToken()
            } returns null

            val actual = repository.areTokensSaved()
            assertEquals(false, actual)
        }
    }

    @Test
    fun tokenStorage_tokensDeleted() {
        runTest {
            coEvery {
                tokenStorage.deleteAll()
            } returns Unit

            repository.deleteTokensFromStorage()
            verify { tokenStorage.deleteAll() }
        }
    }

    @Test
    fun tokenStorage_testSaveTokensToStorage() {
        runTest {
            val token = TokenModel(
                access = "a123",
                refresh = "b456"
            )

            coEvery {
                tokenStorage.setAccessToken(token.access)
            } returns Unit
            coEvery {
                tokenStorage.setRefreshToken(token.refresh)
            } returns Unit

            repository.saveToStorage(token)
            verify { tokenStorage.setAccessToken(token.access) }
            verify { tokenStorage.setRefreshToken(token.refresh) }
        }
    }

    @Test
    fun api_sendLoginToServer() {
        runTest {
            val login = LoginModel(
                email = "volunteer@gmail.com",
                password = "A!23b456"
            )

            val response = LoginJsonResponse(
                accessToken = "a123",
                refreshToken = "b456"
            )

            coEvery {
                authenticationApi.login(login.asJson())
            } returns response

            val actual = repository.sendToServer(login)
            assertEquals(response.asModel(), actual)
        }
    }
}