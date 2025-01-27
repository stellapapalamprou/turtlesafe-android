package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationEvent
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestIncubationViewModelTest {
    private lateinit var viewModel: NestIncubationViewModel
    private val validator: Validator = mockk()

    @Before
    fun setUp() {
        viewModel = NestIncubationViewModel(validator)
    }

    @Test
    fun nestIncubation_updatedStateWithHatcheryCode() {
        runTest {
            every { validator.validateNonEmptyField("N003") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.hatcheryCode)

                viewModel.sendEvent(NestIncubationEvent.HatcheryCodeChanged("N003"))

                val newState = awaitItem()
                assertEquals("N003", newState.hatcheryCode)
                assertEquals("", newState.invalidHatcheryCodeMessage)
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithEmptyHatcheryCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.hatcheryCode)

                viewModel.sendEvent(NestIncubationEvent.HatcheryCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.hatcheryCode)
                assertEquals("Field cannot be empty", newState.invalidHatcheryCodeMessage)
            }
        }
    }

    @Test
    fun nestIncubation_nestSwitchUpdated() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.protectedNestSwitch)

                viewModel.sendEvent(NestIncubationEvent.ProtectedNestSwitchChecked(true))

                val newState = awaitItem()
                assertEquals(true, newState.protectedNestSwitch)
            }
        }
    }

    @Test
    fun nestIncubation_statusUpdated() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.success)

                viewModel.sendEvent(NestIncubationEvent.SaveButtonClicked)

                val newState = awaitItem()
                assertEquals(true, newState.success)
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithCommentsOrRemarks() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.commentsOrRemarks)

                viewModel.sendEvent(NestIncubationEvent.CommentsOrRemarksChanged("Successful incubation"))

                val newState = awaitItem()
                assertEquals("Successful incubation", newState.commentsOrRemarks)
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithIncubationEventAdded() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.incubationDataList.isEmpty())

                viewModel.sendEvent(NestIncubationEvent.IncEventAddButtonClicked)

                val newState = awaitItem()
                assertEquals(false, newState.incubationDataList.isEmpty())
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithIncubationEventDeleted() {
        runTest {
            viewModel.state.test {
                awaitItem()
                viewModel.sendEvent(NestIncubationEvent.IncEventAddButtonClicked)
                awaitItem()
                viewModel.sendEvent(NestIncubationEvent.DeleteIncEventButtonClicked(0))
                val newState = awaitItem()
                assertEquals(true, newState.incubationDataList.isEmpty())
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithNestCode() {
        runTest {
            every { validator.validateNonEmptyField("A123") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NestIncubationEvent.NestCodeChanged("A123"))

                val newState = awaitItem()
                assertEquals("A123", newState.nestCode)
                assertEquals("", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithEmptyNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NestIncubationEvent.NestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.nestCode)
                assertEquals("Field cannot be empty", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithIncubationEvent() {
        runTest {
            every { validator.validateNonEmptyField("Predation") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.incubationDataList.isEmpty())
                viewModel.sendEvent(NestIncubationEvent.IncEventAddButtonClicked)
                awaitItem()
                viewModel.sendEvent(NestIncubationEvent.IncEventTypeSelected(0,"Predation"))
                val newState = awaitItem()
                assertEquals("Predation", newState.incubationDataList[0].selectedIncubationEvent)
                assertEquals("", newState.incubationDataList[0].invalidIncEventMessage)
            }
        }
    }

    @Test
    fun nestIncubation_updatedStateWithEmptyIncubationEvent() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                viewModel.sendEvent(NestIncubationEvent.IncEventAddButtonClicked)
                awaitItem()
                viewModel.sendEvent(NestIncubationEvent.IncEventTypeSelected(0,""))
                val newState = awaitItem()
                assertEquals("", newState.incubationDataList[0].selectedIncubationEvent)
                assertEquals("Field cannot be empty", newState.incubationDataList[0].invalidIncEventMessage)
            }
        }
    }
}