package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationEvent
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestRelocationViewModelTest {
    private lateinit var viewModel: NestRelocationViewModel
    private val validator: Validator = mockk()

    @Before
    fun setUp() {
        viewModel = NestRelocationViewModel(validator)
    }

    @Test
    fun nestRelocation_updatedStateWithDistanceToSea() {
        runTest {
            every { validator.validateNumericText("10.2") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.distanceToSea)

                viewModel.sendEvent(NestRelocationEvent.DistanceToSeaChanged("10.2"))

                val newState = awaitItem()
                assertEquals("10.2", newState.distanceToSea)
                assertEquals("", newState.invalidDistanceToSeaMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithInvalidDistanceToSea() {
        runTest {
            every { validator.validateNumericText("a12") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.distanceToSea)

                viewModel.sendEvent(NestRelocationEvent.DistanceToSeaChanged("a12"))

                val newState = awaitItem()
                assertEquals("a12", newState.distanceToSea)
                assertEquals("Input must be a number", newState.invalidDistanceToSeaMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithNewNestCode() {
        runTest {
            every { validator.validateNonEmptyField("N002") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.newNestCode)

                viewModel.sendEvent(NestRelocationEvent.NewNestCodeChanged("N002"))

                val newState = awaitItem()
                assertEquals("N002", newState.newNestCode)
                assertEquals("", newState.invalidNewNestCodeMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithEmptyNewNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.newNestCode)

                viewModel.sendEvent(NestRelocationEvent.NewNestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.newNestCode)
                assertEquals("Field cannot be empty", newState.invalidNewNestCodeMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithOldNestCode() {
        runTest {
            every { validator.validateNonEmptyField("N002") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.oldNestCode)

                viewModel.sendEvent(NestRelocationEvent.OldNestCodeChanged("N002"))

                val newState = awaitItem()
                assertEquals("N002", newState.oldNestCode)
                assertEquals("", newState.invalidOldNestCodeMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithEmptyOldNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.oldNestCode)

                viewModel.sendEvent(NestRelocationEvent.OldNestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.oldNestCode)
                assertEquals("Field cannot be empty", newState.invalidOldNestCodeMessage)
            }
        }
    }

    @Test
    fun nestRelocation_statusUpdated() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.success)

                viewModel.sendEvent(NestRelocationEvent.SaveButtonClicked)

                val newState = awaitItem()
                assertEquals(true, newState.success)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithDepthBottomNest() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthBottomNest)

                viewModel.sendEvent(NestRelocationEvent.DepthToBottomNestChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.depthBottomNest)
                assertEquals("", newState.invalidDepthBottomNestMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithInvalidDepthBottomNest() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthBottomNest)

                viewModel.sendEvent(NestRelocationEvent.DepthToBottomNestChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.depthBottomNest)
                assertEquals("Input must be a number", newState.invalidDepthBottomNestMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithDepthTopEgg() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthTopEgg)

                viewModel.sendEvent(NestRelocationEvent.DepthTopEggChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.depthTopEgg)
                assertEquals("", newState.invalidDepthTopEggMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithInvalidDepthTopEgg() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthTopEgg)

                viewModel.sendEvent(NestRelocationEvent.DepthTopEggChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.depthTopEgg)
                assertEquals("Input must be a number", newState.invalidDepthTopEggMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithSector() {
        runTest {
            every { validator.validateNonEmptyField("East") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(NestRelocationEvent.SectorSelected("East"))

                val newState = awaitItem()
                assertEquals("East", newState.sector)
                assertEquals("", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithEmptySector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(NestRelocationEvent.SectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.sector)
                assertEquals("Field cannot be empty", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithSubector() {
        runTest {
            every { validator.validateNonEmptyField("A2") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(NestRelocationEvent.SubsectorSelected("A2"))

                val newState = awaitItem()
                assertEquals("A2", newState.subsector)
                assertEquals("", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithEmptySubector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(NestRelocationEvent.SubsectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.subsector)
                assertEquals("Field cannot be empty", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithReasonForRelocation() {
        runTest {
            every { validator.validateNonEmptyField("Erosion Risk") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.reasonForRelocation)

                viewModel.sendEvent(NestRelocationEvent.ReasonForRelocationSelected("Erosion Risk"))

                val newState = awaitItem()
                assertEquals("Erosion Risk", newState.reasonForRelocation)
                assertEquals("", newState.invalidReasonForRelocationMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithEmptyReasonForRelocation() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.reasonForRelocation)

                viewModel.sendEvent(NestRelocationEvent.ReasonForRelocationSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.reasonForRelocation)
                assertEquals("Field cannot be empty", newState.invalidReasonForRelocationMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithReasonForRelocatedTo() {
        runTest {
            every { validator.validateNonEmptyField("BoB") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.relocatedTo)

                viewModel.sendEvent(NestRelocationEvent.RelocatedToSelected("BoB"))

                val newState = awaitItem()
                assertEquals("BoB", newState.relocatedTo)
                assertEquals("", newState.invalidRelocatedToMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithEmptyRelocatedTo() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.relocatedTo)

                viewModel.sendEvent(NestRelocationEvent.RelocatedToSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.relocatedTo)
                assertEquals("Field cannot be empty", newState.invalidRelocatedToMessage)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithNumEggsTransplanted() {
        runTest {
            every { validator.validateIntegerText("4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numOfEggsTransplanted)

                viewModel.sendEvent(NestRelocationEvent.NumOfEggsTransplantedChanged("4"))

                val newState = awaitItem()
                assertEquals("4", newState.numOfEggsTransplanted)
                assertEquals("", newState.invalidNumOfEggsTransplanted)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithInvalidNumEggsTransplanted() {
        runTest {
            every { validator.validateIntegerText("1.3") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numOfEggsTransplanted)

                viewModel.sendEvent(NestRelocationEvent.NumOfEggsTransplantedChanged("1.3"))

                val newState = awaitItem()
                assertEquals("1.3", newState.numOfEggsTransplanted)
                assertEquals("Input must be an integer", newState.invalidNumOfEggsTransplanted)
            }
        }
    }

    @Test
    fun nestRelocation_updatedStateWithCommentsOrRemarks() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.commentsOrRemarks)

                viewModel.sendEvent(NestRelocationEvent.CommentsOrRemarksChanged("Relocation was successful"))

                val newState = awaitItem()
                assertEquals("Relocation was successful", newState.commentsOrRemarks)
            }
        }
    }
}