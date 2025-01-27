package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingEvent
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestHatchingViewModelTest {
    private lateinit var viewModel: NestHatchingViewModel
    private val validator: Validator = mockk()

    @Before
    fun setUp() {
        viewModel = NestHatchingViewModel(validator)
    }

    @Test
    fun nestHatching_updatedStateWithLastDayOfHatching() {
        runTest {
            every { validator.validateNonEmptyField("10/08/2024") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.lastDayHatching)

                viewModel.sendEvent(NestHatchingEvent.LastDayOfHatchingSelected("10/08/2024"))

                val newState = awaitItem()
                assertEquals("10/08/2024", newState.lastDayHatching)
                assertEquals("", newState.invalidLastDayHatchingMessage)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithEmptyLastDayOfHatching() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.lastDayHatching)

                viewModel.sendEvent(NestHatchingEvent.LastDayOfHatchingSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.lastDayHatching)
                assertEquals("Field cannot be empty", newState.invalidLastDayHatchingMessage)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithFirstDayOfHatching() {
        runTest {
            every { validator.validateNonEmptyField("10/08/2024") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.firstDayHatching)

                viewModel.sendEvent(NestHatchingEvent.FirstDayOfHatchingSelected("10/08/2024"))

                val newState = awaitItem()
                assertEquals("10/08/2024", newState.firstDayHatching)
                assertEquals("", newState.invalidFirstDayHatchingMessage)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithEmptyFirstDayOfHatching() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.firstDayHatching)

                viewModel.sendEvent(NestHatchingEvent.FirstDayOfHatchingSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.firstDayHatching)
                assertEquals("Field cannot be empty", newState.invalidFirstDayHatchingMessage)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithCommentsOrRemarks() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.commentsOrRemarks)

                viewModel.sendEvent(NestHatchingEvent.CommentsOrRemarksChanged("Successful hatching"))

                val newState = awaitItem()
                assertEquals("Successful hatching", newState.commentsOrRemarks)
            }
        }
    }

    @Test
    fun nestHatching_statusUpdated() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.success)

                viewModel.sendEvent(NestHatchingEvent.SaveButtonClicked)

                val newState = awaitItem()
                assertEquals(true, newState.success)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithNestCode() {
        runTest {
            every { validator.validateNonEmptyField("A123") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NestHatchingEvent.NestCodeChanged("A123"))

                val newState = awaitItem()
                assertEquals("A123", newState.nestCode)
                assertEquals("", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithEmptyNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NestHatchingEvent.NestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.nestCode)
                assertEquals("Field cannot be empty", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithAddHatchEvent() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.hatchingDataList.isEmpty())

                viewModel.sendEvent(NestHatchingEvent.HatchEventAddButtonClicked)

                val newState = awaitItem()
                assertEquals(false, newState.hatchingDataList.isEmpty())
            }
        }
    }

    @Test
    fun nestHatching_updatedStateWithDeleteHatchEvent() {
        runTest {
            viewModel.state.test {
                awaitItem()
                viewModel.sendEvent(NestHatchingEvent.HatchEventAddButtonClicked)
                awaitItem()
                viewModel.sendEvent(NestHatchingEvent.DeleteHatchEventButtonClicked(0))
                val newState = awaitItem()
                assertEquals(true, newState.hatchingDataList.isEmpty())
            }
        }
    }
}