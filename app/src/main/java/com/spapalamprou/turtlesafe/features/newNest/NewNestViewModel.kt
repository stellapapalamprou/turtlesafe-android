package com.spapalamprou.turtlesafe.features.newNest

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for managing the state of the new nest screen.
 *
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the new nest screen.
 */
@HiltViewModel
class NewNestViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {
    val state: MutableStateFlow<NewNestState> = MutableStateFlow(NewNestState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The NewNestEvent that describes the user action.
     */
    fun sendEvent(event: NewNestEvent) {
        when (event) {
            is NewNestEvent.AreaSelected -> updateArea(event)
            is NewNestEvent.BeachChanged -> updateBeach(event)
            is NewNestEvent.DepthTopEggChanged -> updateDepthTopEgg(event)
            is NewNestEvent.DistanceToSeaChanged -> updateDistanceToSea(event)
            is NewNestEvent.LayingDateSelected -> updateLayingDate(event)
            is NewNestEvent.ProtectedNestSwitchChecked -> updateProtectedNestSwitch(event)
            is NewNestEvent.NestCodeChanged -> updateNestCode(event)
            is NewNestEvent.ProtectionMeasuresSelected -> updateProtectionMeasures(event)
            is NewNestEvent.SectorSelected -> updateSector(event)
            NewNestEvent.SaveButtonClicked -> updateStatus()
            is NewNestEvent.SubsectorSelected -> updateSubsector(event)
            is NewNestEvent.TurtleTagsChanged -> updateTurtleTags(event)
            is NewNestEvent.GpsLatitudeChanged -> updateGpsLatitude(event)
            is NewNestEvent.GpsLongtitudeChanged -> updateGpsLongtitude(event)
            is NewNestEvent.NestLocationReceived -> updateNestLocation(event)
            is NewNestEvent.PhotoChanged -> updatePhotoUri(event)
            is NewNestEvent.CommentsOrRemarksChanged -> updateCommentsOrRemarks(event)
            is NewNestEvent.EmergenceOrEventSelected -> updateEmergenceOrEvent(event)
            is NewNestEvent.TrackTypeSelected -> updateTrackType(event)
        }
    }

    private fun updateLayingDate(event: NewNestEvent.LayingDateSelected) {
        state.update { previous ->
            previous.copy(
                layingDate = event.layingDate,
                invalidLayingDateMessage = validator.validateNonEmptyField(event.layingDate).message
            )
        }
    }

    private fun updateArea(event: NewNestEvent.AreaSelected) {
        state.update { previous ->
            previous.copy(
                area = event.area,
                invalidAreaMessage = validator.validateNonEmptyField(event.area).message
            )
        }
    }

    private fun updateBeach(event: NewNestEvent.BeachChanged) {
        state.update { previous ->
            previous.copy(
                beach = event.beach,
                invalidBeachMessage = validator.validateName(event.beach).message
            )
        }
    }

    private fun updateSector(event: NewNestEvent.SectorSelected) {
        state.update { previous ->
            previous.copy(
                sector = event.sector,
                invalidSectorMessage = validator.validateNonEmptyField(event.sector).message
            )
        }
    }

    private fun updateSubsector(event: NewNestEvent.SubsectorSelected) {
        state.update { previous ->
            previous.copy(
                subsector = event.subsector,
                invalidSubsectorMessage = validator.validateNonEmptyField(event.subsector).message
            )
        }
    }

    private fun updateNestCode(event: NewNestEvent.NestCodeChanged) {
        state.update { previous ->
            previous.copy(
                nestCode = event.nestCode,
                invalidNestCodeMessage = validator.validateNonEmptyField(event.nestCode).message
            )
        }
    }

    private fun updateTrackType(event: NewNestEvent.TrackTypeSelected) {
        state.update { previous ->
            previous.copy(
                trackType = event.trackType,
                invalidTrackTypeMessage = validator.validateNonEmptyField(event.trackType).message
            )
        }
    }

    private fun updateNestLocation(event: NewNestEvent.NestLocationReceived) {
        state.update { previous ->
            previous.copy(
                gpsLatitude = event.gpsLatitude,
                invalidGpsLatitudeMessage = validator.validateNumericText(event.gpsLatitude).message,
                gpsLongtitude = event.gpsLongtitude,
                invalidGpsLongtitudeMessage = validator.validateNumericText(event.gpsLongtitude).message
            )
        }
    }

    private fun updateGpsLatitude(event: NewNestEvent.GpsLatitudeChanged) {
        state.update { previous ->
            previous.copy(
                gpsLatitude = event.gpsLatitude,
                invalidGpsLatitudeMessage = validator.validateNumericText(event.gpsLatitude).message
            )
        }
    }

    private fun updateGpsLongtitude(event: NewNestEvent.GpsLongtitudeChanged) {
        state.update { previous ->
            previous.copy(
                gpsLongtitude = event.gpsLongtitude,
                invalidGpsLongtitudeMessage = validator.validateNumericText(event.gpsLongtitude).message
            )
        }
    }

    private fun updatePhotoUri(event: NewNestEvent.PhotoChanged) {
        state.update { previous ->
            previous.copy(
                photoUri = event.photoUri,
            )
        }
    }

    private fun updateProtectedNestSwitch(event: NewNestEvent.ProtectedNestSwitchChecked) {
        state.update { previous ->
            previous.copy(
                protectedNestSwitch = event.protectedNestSwitch
            )
        }
    }

    private fun updateProtectionMeasures(event: NewNestEvent.ProtectionMeasuresSelected) {
        state.update { previous ->
            previous.copy(
                protectionMeasures = event.protectionMeasures,
                invalidProtectionMeasuresMessage = validator.validateNonEmptyField(event.protectionMeasures).message
            )
        }
    }

    private fun updateTurtleTags(event: NewNestEvent.TurtleTagsChanged) {
        state.update { previous ->
            previous.copy(
                turtleTags = event.turtleTags
            )
        }
    }

    private fun updateEmergenceOrEvent(event: NewNestEvent.EmergenceOrEventSelected) {
        state.update { previous ->
            previous.copy(
                emergenceOrEvent = event.emergenceOrEvent,
                invalidEmergenceOrEventMessage = validator.validateNonEmptyField(event.emergenceOrEvent).message
            )
        }
    }

    private fun updateDepthTopEgg(event: NewNestEvent.DepthTopEggChanged) {
        state.update { previous ->
            previous.copy(
                depthTopEgg = event.depthTopEgg,
                invalidDepthTopEggMessage = validator.validateNumericText(event.depthTopEgg).message
            )
        }
    }

    private fun updateDistanceToSea(event: NewNestEvent.DistanceToSeaChanged) {
        state.update { previous ->
            previous.copy(
                distanceToSea = event.distanceToSea,
                invalidDistanceToSeaMessage = validator.validateNumericText(event.distanceToSea).message
            )
        }
    }

    private fun updateCommentsOrRemarks(event: NewNestEvent.CommentsOrRemarksChanged) {
        state.update { previous ->
            previous.copy(
                commentsOrRemarks = event.commentsOrRemarks
            )
        }
    }

    private fun updateStatus() {
        state.update { previous ->
            previous.copy(success = true)
        }
    }
}