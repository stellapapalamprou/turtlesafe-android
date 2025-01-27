package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import com.spapalamprou.turtlesafe.features.login.LoginEvent
import com.spapalamprou.turtlesafe.features.login.LoginViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private val validator: Validator = mockk()
    private val useCase: LoginUseCase = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = LoginViewModel(validator, useCase)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun shutDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun login_updatedStateWithEmail() {
        runTest {
            every { validator.validateEmail("user@gmail.com") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.email)

                viewModel.sendEvent(LoginEvent.EmailChanged("user@gmail.com"))

                val newState = awaitItem()
                assertEquals("user@gmail.com", newState.email)
                assertEquals("", newState.invalidEmailMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithInvalidEmail() {
        runTest {
            every { validator.validateEmail("user@.com") } returns ValidationResult.EmailError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.email)

                viewModel.sendEvent(LoginEvent.EmailChanged("user@.com"))

                val newState = awaitItem()
                assertEquals("user@.com", newState.email)
                assertEquals("Invalid email", newState.invalidEmailMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithPassword() {
        runTest {
            every { validator.validateNonEmptyField("A!23b456") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.password)

                viewModel.sendEvent(LoginEvent.PasswordChanged("A!23b456"))

                val newState = awaitItem()
                assertEquals("A!23b456", newState.password)
                assertEquals("", newState.invalidPasswordMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithInvalidPassword() {
        runTest {
            every { validator.validateNonEmptyField("A!") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.password)

                viewModel.sendEvent(LoginEvent.PasswordChanged("A!"))

                val newState = awaitItem()
                assertEquals("A!", newState.password)
                assertEquals("Field cannot be empty", newState.invalidPasswordMessage)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_stateSubmitted() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                coEvery { useCase.submit(initialState.transformData()) } returns Unit
                viewModel.sendEvent(LoginEvent.LoginButtonClicked)
                advanceUntilIdle()
                coVerify { useCase.submit(initialState.transformData()) }
            }
        }
    }
}