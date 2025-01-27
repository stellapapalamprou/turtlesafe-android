package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.domain.useCases.LoginUseCase
import com.spapalamprou.turtlesafe.domain.useCases.SignUpUseCase
import com.spapalamprou.turtlesafe.features.signUp.SignUpEvent
import com.spapalamprou.turtlesafe.features.signUp.SignUpViewModel
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

class SignUpViewModelTest {
    private lateinit var viewModel: SignUpViewModel
    private val validator: Validator = mockk()
    private val signUpuseCase: SignUpUseCase = mockk()
    private val loginUseCase: LoginUseCase = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = SignUpViewModel(validator, signUpuseCase, loginUseCase)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun shutDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun signUp_updatedStateWithEmail() {
        runTest {
            every { validator.validateEmail("user@gmail.com") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.email)

                viewModel.sendEvent(SignUpEvent.EmailChanged("user@gmail.com"))

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

                viewModel.sendEvent(SignUpEvent.EmailChanged("user@.com"))

                val newState = awaitItem()
                assertEquals("user@.com", newState.email)
                assertEquals("Invalid email", newState.invalidEmailMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithPassword() {
        runTest {
            every { validator.validatePassword("A!23b456") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.password)

                viewModel.sendEvent(SignUpEvent.PasswordChanged("A!23b456"))

                val newState = awaitItem()
                assertEquals("A!23b456", newState.password)
                assertEquals("", newState.invalidPasswordMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithInvalidPassword() {
        runTest {
            every { validator.validatePassword("A!") } returns ValidationResult.PasswordError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.password)

                viewModel.sendEvent(SignUpEvent.PasswordChanged("A!"))

                val newState = awaitItem()
                assertEquals("A!", newState.password)
                assertEquals("Invalid password", newState.invalidPasswordMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithFirstName() {
        runTest {
            every { validator.validateName("Mary") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.firstName)

                viewModel.sendEvent(SignUpEvent.FirstNameChanged("Mary"))

                val newState = awaitItem()
                assertEquals("Mary", newState.firstName)
                assertEquals("", newState.invalidFirstNameMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithInvalidFirstName() {
        runTest {
            every { validator.validateName("123") } returns ValidationResult.NameError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.firstName)

                viewModel.sendEvent(SignUpEvent.FirstNameChanged("123"))

                val newState = awaitItem()
                assertEquals("123", newState.firstName)
                assertEquals("Invalid input", newState.invalidFirstNameMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithLastName() {
        runTest {
            every { validator.validateName("Jane") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.lastName)

                viewModel.sendEvent(SignUpEvent.LastNameChanged("Jane"))

                val newState = awaitItem()
                assertEquals("Jane", newState.lastName)
                assertEquals("", newState.invalidLastNameMessage)
            }
        }
    }

    @Test
    fun login_updatedStateWithInvalidLastName() {
        runTest {
            every { validator.validateName("123") } returns ValidationResult.NameError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.lastName)

                viewModel.sendEvent(SignUpEvent.LastNameChanged("123"))

                val newState = awaitItem()
                assertEquals("123", newState.lastName)
                assertEquals("Invalid input", newState.invalidLastNameMessage)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_stateSubmitted() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                coEvery { signUpuseCase.submit(initialState.transformData()) } returns Unit
                coEvery { loginUseCase.submit(initialState.transformDataToLogin()) } returns Unit
                viewModel.sendEvent(SignUpEvent.SignUpButtonClicked)
                advanceUntilIdle()
                coVerify { signUpuseCase.submit(initialState.transformData()) }
                coVerify { loginUseCase.submit(initialState.transformDataToLogin()) }
            }
        }
    }
}