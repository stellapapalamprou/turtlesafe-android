package com.spapalamprou.turtlesafe.features

import app.cash.turbine.test
import com.spapalamprou.turtlesafe.features.newNest.NewNestEvent
import com.spapalamprou.turtlesafe.features.newNest.NewNestViewModel
import com.spapalamprou.turtlesafe.validators.ValidationResult
import com.spapalamprou.turtlesafe.validators.Validator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewNestViewModelTest {
    private lateinit var viewModel: NewNestViewModel
    private val validator: Validator = mockk()

    @Before
    fun setUp() {
        viewModel = NewNestViewModel(validator)
    }

    @Test
    fun newNest_updatedStateWithNestCode() {
        runTest {
            every { validator.validateNonEmptyField("N001") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NewNestEvent.NestCodeChanged("N001"))

                val newState = awaitItem()
                assertEquals("N001", newState.nestCode)
                assertEquals("", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithEmptyNestCode() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.nestCode)

                viewModel.sendEvent(NewNestEvent.NestCodeChanged(""))

                val newState = awaitItem()
                assertEquals("", newState.nestCode)
                assertEquals("Field cannot be empty", newState.invalidNestCodeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithCommentsOrRemarks() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.commentsOrRemarks)

                viewModel.sendEvent(NewNestEvent.CommentsOrRemarksChanged("Successful nesting"))

                val newState = awaitItem()
                assertEquals("Successful nesting", newState.commentsOrRemarks)
            }
        }
    }

    @Test
    fun newNest_statusUpdated() {
        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.success)

                viewModel.sendEvent(NewNestEvent.SaveButtonClicked)

                val newState = awaitItem()
                assertEquals(true, newState.success)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithArea() {
        runTest {
            every { validator.validateNonEmptyField("LAK") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.area)

                viewModel.sendEvent(NewNestEvent.AreaSelected("LAK"))

                val newState = awaitItem()
                assertEquals("LAK", newState.area)
                assertEquals("", newState.invalidAreaMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithEmptyArea() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.area)

                viewModel.sendEvent(NewNestEvent.AreaSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.area)
                assertEquals("Field cannot be empty", newState.invalidAreaMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithSector() {
        runTest {
            every { validator.validateNonEmptyField("East") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(NewNestEvent.SectorSelected("East"))

                val newState = awaitItem()
                assertEquals("East", newState.sector)
                assertEquals("", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithEmptySector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.sector)

                viewModel.sendEvent(NewNestEvent.SectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.sector)
                assertEquals("Field cannot be empty", newState.invalidSectorMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithSubector() {
        runTest {
            every { validator.validateNonEmptyField("A2") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(NewNestEvent.SubsectorSelected("A2"))

                val newState = awaitItem()
                assertEquals("A2", newState.subsector)
                assertEquals("", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithEmptySubector() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.subsector)

                viewModel.sendEvent(NewNestEvent.SubsectorSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.subsector)
                assertEquals("Field cannot be empty", newState.invalidSubsectorMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithBeach() {
        runTest {
            every { validator.validateName("Selinitsa") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.beach)

                viewModel.sendEvent(NewNestEvent.BeachChanged("Selinitsa"))

                val newState = awaitItem()
                assertEquals("Selinitsa", newState.beach)
                assertEquals("", newState.invalidBeachMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithEmptyBeach() {
        runTest {
            every { validator.validateName("1") } returns ValidationResult.NameError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.beach)

                viewModel.sendEvent(NewNestEvent.BeachChanged("1"))

                val newState = awaitItem()
                assertEquals("1", newState.beach)
                assertEquals("Invalid input", newState.invalidBeachMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithDepthTopEgg() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthTopEgg)

                viewModel.sendEvent(NewNestEvent.DepthTopEggChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.depthTopEgg)
                assertEquals("", newState.invalidDepthTopEggMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithInvalidDepthTopEgg() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.depthTopEgg)

                viewModel.sendEvent(NewNestEvent.DepthTopEggChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.depthTopEgg)
                assertEquals("Input must be a number", newState.invalidDepthTopEggMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithDistanceToSea() {
        runTest {
            every { validator.validateNumericText("1.4") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.distanceToSea)

                viewModel.sendEvent(NewNestEvent.DistanceToSeaChanged("1.4"))

                val newState = awaitItem()
                assertEquals("1.4", newState.distanceToSea)
                assertEquals("", newState.invalidDistanceToSeaMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithInvalidDistanceToSea() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.distanceToSea)

                viewModel.sendEvent(NewNestEvent.DistanceToSeaChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.distanceToSea)
                assertEquals("Input must be a number", newState.invalidDistanceToSeaMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithGpsLatitude() {
        runTest {
            every { validator.validateNumericText("1.4345677") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.gpsLatitude)

                viewModel.sendEvent(NewNestEvent.GpsLatitudeChanged("1.4345677"))

                val newState = awaitItem()
                assertEquals("1.4345677", newState.gpsLatitude)
                assertEquals("", newState.invalidGpsLatitudeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithInvalidGpsLatitude() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.gpsLatitude)

                viewModel.sendEvent(NewNestEvent.GpsLatitudeChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.gpsLatitude)
                assertEquals("Input must be a number", newState.invalidGpsLatitudeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithGpsLongtitude() {
        runTest {
            every { validator.validateNumericText("1.4345677") } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.gpsLongtitude)

                viewModel.sendEvent(NewNestEvent.GpsLongtitudeChanged("1.4345677"))

                val newState = awaitItem()
                assertEquals("1.4345677", newState.gpsLongtitude)
                assertEquals("", newState.invalidGpsLongtitudeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithInvalidGpsLongtitude() {
        runTest {
            every { validator.validateNumericText("3") } returns ValidationResult.NumericTextError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.gpsLongtitude)

                viewModel.sendEvent(NewNestEvent.GpsLongtitudeChanged("3"))

                val newState = awaitItem()
                assertEquals("3", newState.gpsLongtitude)
                assertEquals("Input must be a number", newState.invalidGpsLongtitudeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithTrackType() {
        runTest {
            every { validator.validateNonEmptyField("") } returns ValidationResult.NonEmptyError

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.trackType)

                viewModel.sendEvent(NewNestEvent.TrackTypeSelected(""))

                val newState = awaitItem()
                assertEquals("", newState.trackType)
                assertEquals("Field cannot be empty", newState.invalidTrackTypeMessage)
            }
        }
    }

    @Test
    fun newNest_updatedStateWithNestLocation() {
        runTest {
            every { validator.validateNumericText(any()) } returns ValidationResult.Success

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals("", initialState.gpsLatitude)
                assertEquals("", initialState.gpsLongtitude)

                viewModel.sendEvent(NewNestEvent.NestLocationReceived("1.4345677", "1.4321"))

                val newState = awaitItem()
                assertEquals("1.4345677", newState.gpsLatitude)
                assertEquals("", newState.invalidGpsLatitudeMessage)
                assertEquals("1.4321", newState.gpsLongtitude)
                assertEquals("", newState.invalidGpsLongtitudeMessage)
            }
        }
    }
}