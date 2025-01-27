package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.domain.useCases.GetNestCodeUseCase
import com.spapalamprou.turtlesafe.domain.useCases.MorningSurveyUseCase
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationState
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingState
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationState
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationState
import com.spapalamprou.turtlesafe.features.newNest.NewNestState
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

class MorningSurveyViewModelTest {
    private lateinit var viewModel: MorningSurveyViewModel
    private val morningSurveyUseCase: MorningSurveyUseCase = mockk()
    private val getNestCodeUseCase: GetNestCodeUseCase = mockk()
    private val validator: Validator = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = MorningSurveyViewModel(morningSurveyUseCase, getNestCodeUseCase, validator)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun shutDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun morningSurvey_updatedStateWithBeach() {
        runTest {
            every { validator.validateName("Selinitsa") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.beach)

                viewModel.sendEvent(MorningSurveyEvent.BeachChanged("Selinitsa"))

                val newState = awaitItem()
                assertEquals("Selinitsa", newState.beach)
                assertEquals("", newState.invalidBeachMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithInvalidBeachName() {
        runTest {
            every { validator.validateName("123") } returns ValidationResult.NameError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.beach)

                viewModel.sendEvent(MorningSurveyEvent.BeachChanged("123"))

                val newState = awaitItem()
                assertEquals("123", newState.beach)
                assertEquals("Invalid input", newState.invalidBeachMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithTime() {
        runTest {
            every { validator.validateNonEmptyField("12:00") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.time)

                viewModel.sendEvent(MorningSurveyEvent.TimeSelected("12:00"))

                val newState = awaitItem()
                assertEquals("12:00", newState.time)
                assertEquals("", newState.invalidTimeMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithEmptyTime() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.time)

                viewModel.sendEvent(MorningSurveyEvent.TimeSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.time)
                assertEquals("Field cannot be empty", newState.invalidTimeMessage)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun morningSurvey_stateSubmitted() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                coEvery { morningSurveyUseCase.submit(initialState.transformData()) } returns Unit

                viewModel.sendEvent(MorningSurveyEvent.SubmitButtonClicked)
                val newState = awaitItem()
                advanceUntilIdle()
                coVerify { morningSurveyUseCase.submit(newState.transformData()) }
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithArea() {
        runTest {
            every { validator.validateNonEmptyField("LAK") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.area)

                viewModel.sendEvent(MorningSurveyEvent.AreaSelected("LAK"))

                val newState = awaitItem()
                assertEquals("LAK", newState.area)
                assertEquals("", newState.invalidAreaMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithEmptyArea() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.area)

                viewModel.sendEvent(MorningSurveyEvent.AreaSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.area)
                assertEquals("Field cannot be empty", newState.invalidAreaMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNestCode() {
        runTest {
            every { validator.validateNonEmptyField("A123") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(MorningSurveyEvent.NestCodeSelected("A123"))

                val newState = awaitItem()
                assertEquals("A123", newState.nestCode)
                assertEquals("", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithEmptyNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(MorningSurveyEvent.NestCodeSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.nestCode)
                assertEquals("Field cannot be empty", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithCommentsOrRemarks() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.commentsOrRemarks)

                viewModel.sendEvent(MorningSurveyEvent.CommentsOrRemarksChanged("Successful nesting"))

                val newState = awaitItem()
                assertEquals("Successful nesting", newState.commentsOrRemarks)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithSector() {
        runTest {
            every { validator.validateNonEmptyField("East") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(MorningSurveyEvent.SectorSelected("East"))

                val newState = awaitItem()
                assertEquals("East", newState.sector)
                assertEquals("", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithEmptySector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(MorningSurveyEvent.SectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.sector)
                assertEquals("Field cannot be empty", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithSubector() {
        runTest {
            every { validator.validateNonEmptyField("A2") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(MorningSurveyEvent.SubsectorSelected("A2"))

                val newState = awaitItem()
                assertEquals("A2", newState.subsector)
                assertEquals("", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithEmptySubector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(MorningSurveyEvent.SubsectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.subsector)
                assertEquals("Field cannot be empty", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithDate() {
        runTest {
            every { validator.validateNonEmptyField("10/08/2024") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.date)

                viewModel.sendEvent(MorningSurveyEvent.DateSelected("10/08/2024"))

                val newState = awaitItem()
                assertEquals("10/08/2024", newState.date)
                assertEquals("", newState.invalidDateMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithEmptyDate() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.date)

                viewModel.sendEvent(MorningSurveyEvent.DateSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.date)
                assertEquals("Field cannot be empty", newState.invalidDateMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNumOfAttempts() {
        runTest {
            every { validator.validateIntegerText("4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numberOfAttempts)

                viewModel.sendEvent(MorningSurveyEvent.NumberOfAttemptsChanged("4"))

                val newState = awaitItem()
                assertEquals("4", newState.numberOfAttempts)
                assertEquals("", newState.invalidNumberOfAttemptsMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithInvalidNumOfAttempts() {
        runTest {
            every { validator.validateIntegerText("1.3") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numberOfAttempts)

                viewModel.sendEvent(MorningSurveyEvent.NumberOfAttemptsChanged("1.3"))

                val newState = awaitItem()
                assertEquals("1.3", newState.numberOfAttempts)
                assertEquals("Input must be an integer", newState.invalidNumberOfAttemptsMessage)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNestingAttemptSwitch() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.nestingAttemptSwitch)

                viewModel.sendEvent(MorningSurveyEvent.NestingAttemptSwitchChecked(true))

                val newState = awaitItem()
                assertEquals(true, newState.nestingAttemptSwitch)
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNewNestAdded() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.newNestList.isEmpty())

                val newNestState = NewNestState()
                viewModel.sendEvent(MorningSurveyEvent.NewNestAdded(newNestState))

                val newState = awaitItem()
                assertEquals(true, newState.newNestList.isNotEmpty())
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNestRelocationAdded() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.nestRelocationList.isEmpty())

                val nestRelocationState = NestRelocationState()
                viewModel.sendEvent(MorningSurveyEvent.NestRelocationAdded(nestRelocationState))

                val newState = awaitItem()
                assertEquals(true, newState.nestRelocationList.isNotEmpty())
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNestExcavationAdded() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.nestExcavationList.isEmpty())

                val nestExcavationState = NestExcavationState()
                viewModel.sendEvent(MorningSurveyEvent.NestExcavationAdded(nestExcavationState))

                val newState = awaitItem()
                assertEquals(true, newState.nestExcavationList.isNotEmpty())
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNestHatchingAdded() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.nestHatchingList.isEmpty())

                val nestHatchingState = NestHatchingState()
                viewModel.sendEvent(MorningSurveyEvent.NestHatchingAdded(nestHatchingState))

                val newState = awaitItem()
                assertEquals(true, newState.nestHatchingList.isNotEmpty())
            }
        }
    }

    @Test
    fun morningSurvey_updatedStateWithNestIncubationAdded() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.nestIncubationList.isEmpty())

                val nestIncubationState = NestIncubationState()
                viewModel.sendEvent(MorningSurveyEvent.NestIncubationAdded(nestIncubationState))

                val newState = awaitItem()
                assertEquals(true, newState.nestIncubationList.isNotEmpty())
            }
        }
    }
}