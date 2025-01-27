package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationEvent
import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestExcavationViewModelTest {
    private lateinit var viewModel: NestExcavationViewModel
    private val validator: Validator = mockk()

    @Before
    fun setUp() {
        viewModel = NestExcavationViewModel(validator)
    }

    @Test
    fun nestExcavation_updatedStateWithHatchedEggs() {
        runTest {
            every { validator.validateIntegerText("10") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.hatchedEggs)

                viewModel.sendEvent(NestExcavationEvent.HatchedEggsChanged("10"))

                val newState = awaitItem()
                assertEquals("10", newState.hatchedEggs)
                assertEquals("", newState.invalidHatchedEggsMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidHatchedEggs() {
        runTest {
            every { validator.validateIntegerText("a12") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.hatchedEggs)

                viewModel.sendEvent(NestExcavationEvent.HatchedEggsChanged("a12"))

                val newState = awaitItem()
                assertEquals("a12", newState.hatchedEggs)
                assertEquals("Input must be an integer", newState.invalidHatchedEggsMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithCommentsOrRemarks() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.commentsOrRemarks)

                viewModel.sendEvent(NestExcavationEvent.CommentsOrRemarksChanged("Successful hatching"))

                val newState = awaitItem()
                assertEquals("Successful hatching", newState.commentsOrRemarks)
            }
        }
    }

    @Test
    fun nestExcavation_statusUpdated() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.success)

                viewModel.sendEvent(NestExcavationEvent.SaveButtonClicked)

                val newState = awaitItem()
                assertEquals(true, newState.success)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithPippedDeadHatchlings() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.pippedDeadHatchlings)

                viewModel.sendEvent(NestExcavationEvent.PippedDeadHatchlingsChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.pippedDeadHatchlings)
                assertEquals("", newState.invalidPippedDeadHatchlingsMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidPippedDeadHatchlings() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.pippedDeadHatchlings)

                viewModel.sendEvent(NestExcavationEvent.PippedDeadHatchlingsChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.pippedDeadHatchlings)
                assertEquals("Input must be an integer", newState.invalidPippedDeadHatchlingsMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithPippedLiveHatchlings() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.pippedLiveHatchlings)

                viewModel.sendEvent(NestExcavationEvent.PippedLiveHatchlingsChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.pippedLiveHatchlings)
                assertEquals("", newState.invalidPippedLiveHatchlingsMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidPippedLiveHatchlings() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.pippedLiveHatchlings)

                viewModel.sendEvent(NestExcavationEvent.PippedLiveHatchlingsChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.pippedLiveHatchlings)
                assertEquals("Input must be an integer", newState.invalidPippedLiveHatchlingsMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithNoEmbryos() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.noEmbryos)

                viewModel.sendEvent(NestExcavationEvent.NoEmbryosChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.noEmbryos)
                assertEquals("", newState.invalidNoEmbryosMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidNoEmbryos() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.noEmbryos)

                viewModel.sendEvent(NestExcavationEvent.NoEmbryosChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.noEmbryos)
                assertEquals("Input must be an integer", newState.invalidNoEmbryosMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithDeadEmbryos() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.deadEmbryos)

                viewModel.sendEvent(NestExcavationEvent.DeadEmbryosChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.deadEmbryos)
                assertEquals("", newState.invalidDeadEmbryosMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidDeadEmbryos() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.deadEmbryos)

                viewModel.sendEvent(NestExcavationEvent.DeadEmbryosChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.deadEmbryos)
                assertEquals("Input must be an integer", newState.invalidDeadEmbryosMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithLiveEmbryos() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.liveEmbryos)

                viewModel.sendEvent(NestExcavationEvent.LiveEmbryosChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.liveEmbryos)
                assertEquals("", newState.invalidLiveEmbryosMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidLiveEmbryos() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.liveEmbryos)

                viewModel.sendEvent(NestExcavationEvent.LiveEmbryosChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.liveEmbryos)
                assertEquals("Input must be an integer", newState.invalidLiveEmbryosMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithDeadHatchlings() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.deadHatchlingsNest)

                viewModel.sendEvent(NestExcavationEvent.DeadHatchlingsNestChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.deadHatchlingsNest)
                assertEquals("", newState.invalidDeadHatchlingsNestMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidDeadHatchlings() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.deadHatchlingsNest)

                viewModel.sendEvent(NestExcavationEvent.DeadHatchlingsNestChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.deadHatchlingsNest)
                assertEquals("Input must be an integer", newState.invalidDeadHatchlingsNestMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithLiveHatchlings() {
        runTest {
            every { validator.validateIntegerText("1") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.liveHatchlingsNest)

                viewModel.sendEvent(NestExcavationEvent.LiveHatchlingsNestChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.liveHatchlingsNest)
                assertEquals("", newState.invalidLiveHatchlingsNestMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithInvalidLiveHatchlings() {
        runTest {
            every { validator.validateIntegerText("3.2") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.liveHatchlingsNest)

                viewModel.sendEvent(NestExcavationEvent.LiveHatchlingsNestChanged("3.2"))

                val newState = awaitItem()
                assertEquals("3.2", newState.liveHatchlingsNest)
                assertEquals("Input must be an integer", newState.invalidLiveHatchlingsNestMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithNestCode() {
        runTest {
            every { validator.validateNonEmptyField("A123") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NestExcavationEvent.NestCodeChanged("A123"))

                val newState = awaitItem()
                assertEquals("A123", newState.nestCode)
                assertEquals("", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nestExcavation_updatedStateWithEmptyNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NestExcavationEvent.NestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.nestCode)
                assertEquals("Field cannot be empty", newState.invalidNestCodeMessage)
            }
        }
    }
}