package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.domain.useCases.NightSurveyUseCase
import com.spapalamprou.turtlesafe.features.nightSurvey.NightSurveyEvent
import com.spapalamprou.turtlesafe.features.nightSurvey.NightSurveyViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NightSurveyViewModelTest {
    private lateinit var viewModel: NightSurveyViewModel
    private val useCase: NightSurveyUseCase = mockk()
    private val validator: Validator = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = NightSurveyViewModel(useCase, validator)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun shutDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun nightSurvey_updatedStateWithArea() {
        runTest {
            every { validator.validateNonEmptyField("LAK") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.area)

                viewModel.sendEvent(NightSurveyEvent.AreaSelected("LAK"))

                val newState = awaitItem()
                assertEquals("LAK", newState.area)
                assertEquals("", newState.invalidAreaMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithEmptyArea() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.area)

                viewModel.sendEvent(NightSurveyEvent.AreaSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.area)
                assertEquals("Field cannot be empty", newState.invalidAreaMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithSector() {
        runTest {
            every { validator.validateNonEmptyField("East") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(NightSurveyEvent.SectorSelected("East"))

                val newState = awaitItem()
                assertEquals("East", newState.sector)
                assertEquals("", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithEmptySector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(NightSurveyEvent.SectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.sector)
                assertEquals("Field cannot be empty", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithSubector() {
        runTest {
            every { validator.validateNonEmptyField("A2") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(NightSurveyEvent.SubsectorSelected("A2"))

                val newState = awaitItem()
                assertEquals("A2", newState.subsector)
                assertEquals("", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithEmptySubector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(NightSurveyEvent.SubsectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.subsector)
                assertEquals("Field cannot be empty", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithNestCode() {
        runTest {
            every { validator.validateNonEmptyField("A123") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NightSurveyEvent.NestCodeChanged("A123"))

                val newState = awaitItem()
                assertEquals("A123", newState.nestCode)
                assertEquals("", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithEmptyNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NightSurveyEvent.NestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.nestCode)
                assertEquals("Field cannot be empty", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithBeach() {
        runTest {
            every { validator.validateName("Selinitsa") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.beach)

                viewModel.sendEvent(NightSurveyEvent.BeachChanged("Selinitsa"))

                val newState = awaitItem()
                assertEquals("Selinitsa", newState.beach)
                assertEquals("", newState.invalidBeachMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithEmptyBeach() {
        runTest {
            every { validator.validateName("1") } returns ValidationResult.NameError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.beach)

                viewModel.sendEvent(NightSurveyEvent.BeachChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.beach)
                assertEquals("Invalid input", newState.invalidBeachMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithTagger() {
        runTest {
            every { validator.validateName("Mary Jane") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.tagger)

                viewModel.sendEvent(NightSurveyEvent.TaggerChanged("Mary Jane"))

                val newState = awaitItem()
                assertEquals("Mary Jane", newState.tagger)
                assertEquals("", newState.invalidTaggerMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithEmptyTagger() {
        runTest {
            every { validator.validateName("Mary Jane") } returns ValidationResult.NameError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.tagger)

                viewModel.sendEvent(NightSurveyEvent.TaggerChanged("Mary Jane"))

                val newState = awaitItem()
                assertEquals("Mary Jane", newState.tagger)
                assertEquals("Invalid input", newState.invalidTaggerMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithCurvedCarapaceLength() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.curvedCarapaceLength)

                viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceLengthChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.curvedCarapaceLength)
                assertEquals("", newState.invalidCurvedCarapaceLengthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidCurvedCarapaceLength() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.curvedCarapaceLength)

                viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceLengthChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.curvedCarapaceLength)
                assertEquals("Input must be a number", newState.invalidCurvedCarapaceLengthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithCurvedCarapaceWidth() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.curvedCarapaceWidth)

                viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceWidthChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.curvedCarapaceWidth)
                assertEquals("", newState.invalidCurvedCarapaceWidthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidCurvedCarapaceWidth() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.curvedCarapaceWidth)

                viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceWidthChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.curvedCarapaceWidth)
                assertEquals("Input must be a number", newState.invalidCurvedCarapaceWidthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithStraightCarapaceLength() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.straightCarapaceLength)

                viewModel.sendEvent(NightSurveyEvent.StraightCarapaceLengthChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.straightCarapaceLength)
                assertEquals("", newState.invalidStraightCarapaceLengthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidStraightCarapaceLength() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.straightCarapaceLength)

                viewModel.sendEvent(NightSurveyEvent.StraightCarapaceLengthChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.straightCarapaceLength)
                assertEquals(
                    "Input must be a number",
                    newState.invalidStraightCarapaceLengthMessage
                )
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithStraightCarapaceWidth() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.straightCarapaceWidth)

                viewModel.sendEvent(NightSurveyEvent.StraightCarapaceWidthChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.straightCarapaceWidth)
                assertEquals("", newState.invalidStraightCarapaceWidthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidStraightCarapaceWidth() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.straightCarapaceWidth)

                viewModel.sendEvent(NightSurveyEvent.StraightCarapaceWidthChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.straightCarapaceWidth)
                assertEquals("Input must be a number", newState.invalidStraightCarapaceWidthMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDepthBottomNest() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthBottomNests)

                viewModel.sendEvent(NightSurveyEvent.DepthToBottomNestsChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.depthBottomNests)
                assertEquals("", newState.invalidDepthBottomNestMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidDepthBottomNest() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthBottomNests)

                viewModel.sendEvent(NightSurveyEvent.DepthToBottomNestsChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.depthBottomNests)
                assertEquals("Input must be a number", newState.invalidDepthBottomNestMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDepthTopEgg() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthTopEgg)

                viewModel.sendEvent(NightSurveyEvent.DepthTopEggChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.depthTopEgg)
                assertEquals("", newState.invalidDepthTopEggMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidDepthTopEgg() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthTopEgg)

                viewModel.sendEvent(NightSurveyEvent.DepthTopEggChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.depthTopEgg)
                assertEquals("Input must be a number", newState.invalidDepthTopEggMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDistanceToSea() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.distanceToSea)

                viewModel.sendEvent(NightSurveyEvent.DistanceToSeaChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.distanceToSea)
                assertEquals("", newState.invalidDistanceToSeaMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidDistanceToSea() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.distanceToSea)

                viewModel.sendEvent(NightSurveyEvent.DistanceToSeaChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.distanceToSea)
                assertEquals("Input must be a number", newState.invalidDistanceToSeaMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithNumEggsRelocated() {
        runTest {
            every { validator.validateIntegerText("4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numEggsRelocated)

                viewModel.sendEvent(NightSurveyEvent.NumOfEggsRelocatedChanged("4"))

                val newState = awaitItem()
                assertEquals("4", newState.numEggsRelocated)
                assertEquals("", newState.invalidNumEggsRelocatedMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidNumEggsRelocated() {
        runTest {
            every { validator.validateIntegerText("1.3") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numEggsRelocated)

                viewModel.sendEvent(NightSurveyEvent.NumOfEggsRelocatedChanged("1.3"))

                val newState = awaitItem()
                assertEquals("1.3", newState.numEggsRelocated)
                assertEquals("Input must be an integer", newState.invalidNumEggsRelocatedMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithNumEggsExcavated() {
        runTest {
            every { validator.validateIntegerText("4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numEggsExcavated)

                viewModel.sendEvent(NightSurveyEvent.NumOfEggsExcavatedChanged("4"))

                val newState = awaitItem()
                assertEquals("4", newState.numEggsExcavated)
                assertEquals("", newState.invalidNumEggsExcavatedMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithInvalidNumEggsExcavated() {
        runTest {
            every { validator.validateIntegerText("1.3") } returns ValidationResult.IntegerTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.numEggsExcavated)

                viewModel.sendEvent(NightSurveyEvent.NumOfEggsExcavatedChanged("1.3"))

                val newState = awaitItem()
                assertEquals("1.3", newState.numEggsExcavated)
                assertEquals("Input must be an integer", newState.invalidNumEggsExcavatedMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithStartLayingTime() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.startLaying)

                viewModel.sendEvent(NightSurveyEvent.StartLayingSelected("12:00"))

                val newState = awaitItem()
                assertEquals("12:00", newState.startLaying)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithStartCamouflageTime() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.startCamouflage)

                viewModel.sendEvent(NightSurveyEvent.StartCamouflageSelected("12:00"))

                val newState = awaitItem()
                assertEquals("12:00", newState.startCamouflage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDepartNestSiteTime() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.departNestSite)

                viewModel.sendEvent(NightSurveyEvent.DepartNestSiteSelected("12:00"))

                val newState = awaitItem()
                assertEquals("12:00", newState.departNestSite)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithTurtleAtSeaTime() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.turtleAtSea)

                viewModel.sendEvent(NightSurveyEvent.TurtleAtSeaSelected("12:00"))

                val newState = awaitItem()
                assertEquals("12:00", newState.turtleAtSea)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithRelocationComments() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.relocationComments)

                viewModel.sendEvent(NightSurveyEvent.RelocationCommentsChanged("Successful relocation"))

                val newState = awaitItem()
                assertEquals("Successful relocation", newState.relocationComments)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithNestingEmergence() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.nestEmergenceSwitch)

                viewModel.sendEvent(NightSurveyEvent.NestingEmergenceSwitchChecked(true))

                val newState = awaitItem()
                assertEquals(true, newState.nestEmergenceSwitch)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDamageFlippersHead() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.damageFlippersHead)

                viewModel.sendEvent(NightSurveyEvent.DamageFlippersHeadChanged("C1"))

                val newState = awaitItem()
                assertEquals("C1", newState.damageFlippersHead)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDamageCarapace() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.damageCarapace)

                viewModel.sendEvent(NightSurveyEvent.DamageCarapaceChanged("C1"))

                val newState = awaitItem()
                assertEquals("C1", newState.damageCarapace)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithAddOldTag() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.oldTagDataList.isEmpty())

                viewModel.sendEvent(NightSurveyEvent.AddOldTagButtonClicked)

                val newState = awaitItem()
                assertEquals(false, newState.oldTagDataList.isEmpty())
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDeleteOldTag() {
        runTest {
            viewModel.state.test {
                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.AddOldTagButtonClicked)
                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.DeleteOldTagButtonClicked(0))
                val newState = awaitItem()
                assertEquals(true, newState.oldTagDataList.isEmpty())
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithOldTagLocation() {
        runTest {
            viewModel.state.test {
                every { validator.validateNonEmptyField("Front Left Flipper") } returns ValidationResult.Success

                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.AddOldTagButtonClicked)
                awaitItem()
                viewModel.sendEvent(
                    NightSurveyEvent.OldTagLocationSelected(
                        0,
                        "Front Left Flipper"
                    )
                )
                val newState = awaitItem()
                assertEquals(
                    "Front Left Flipper",
                    newState.oldTagDataList[0].selectedOldTagLocation
                )
                assertEquals("", newState.oldTagDataList[0].invalidOldTagLocationMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithOldTagCode() {
        runTest {
            viewModel.state.test {
                every { validator.validateNonEmptyField("OLDCODE123") } returns ValidationResult.Success

                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.AddOldTagButtonClicked)
                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.OldTagCodeChanged(0, "OLDCODE123"))
                val newState = awaitItem()
                assertEquals("OLDCODE123", newState.oldTagDataList[0].oldTagCode)
                assertEquals("", newState.oldTagDataList[0].invalidOldTagCodeMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithAddNewTag() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(true, initialState.newTagDataList.isEmpty())

                viewModel.sendEvent(NightSurveyEvent.AddNewTagButtonClicked)

                val newState = awaitItem()
                assertEquals(false, newState.newTagDataList.isEmpty())
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithDeleteNewTag() {
        runTest {
            viewModel.state.test {
                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.AddNewTagButtonClicked)
                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.DeleteNewTagButtonClicked(0))
                val newState = awaitItem()
                assertEquals(true, newState.newTagDataList.isEmpty())
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithNewTagLocation() {
        runTest {
            viewModel.state.test {
                every { validator.validateNonEmptyField("Front Left Flipper") } returns ValidationResult.Success

                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.AddNewTagButtonClicked)
                awaitItem()
                viewModel.sendEvent(
                    NightSurveyEvent.NewTagLocationSelected(
                        0,
                        "Front Left Flipper"
                    )
                )
                val newState = awaitItem()
                assertEquals(
                    "Front Left Flipper",
                    newState.newTagDataList[0].selectedNewTagLocation
                )
                assertEquals("", newState.newTagDataList[0].invalidNewTagLocationMessage)
            }
        }
    }

    @Test
    fun nightSurvey_updatedStateWithNewTagCode() {
        runTest {
            viewModel.state.test {
                every { validator.validateNonEmptyField("NEWCODE123") } returns ValidationResult.Success

                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.AddNewTagButtonClicked)
                awaitItem()
                viewModel.sendEvent(NightSurveyEvent.NewTagCodeChanged(0, "NEWCODE123"))
                val newState = awaitItem()
                assertEquals("NEWCODE123", newState.newTagDataList[0].newTagCode)
                assertEquals("", newState.newTagDataList[0].invalidNewTagCodeMessage)
            }
        }
    }
}