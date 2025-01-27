package com.spapalamprou.turtlesafe.features.nestRelocation

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for managing the state of the nest relocation screen.
 *
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the nest relocation screen.
 */
@HiltViewModel
class NestRelocationViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {
    val state: MutableStateFlow<NestRelocationState> = MutableStateFlow(NestRelocationState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The NestRelocationEvent that describes the user action.
     */
    fun sendEvent(event: NestRelocationEvent) {
        when (event) {
            is NestRelocationEvent.DepthToBottomNestChanged -> updateDepthBottomNest(event)
            is NestRelocationEvent.DepthTopEggChanged -> updateDepthTopEgg(event)
            is NestRelocationEvent.DistanceToSeaChanged -> updateDistanceToSea(event)
            is NestRelocationEvent.NewNestCodeChanged -> updateNewNestCode(event)
            is NestRelocationEvent.NumOfEggsTransplantedChanged -> updateNumEggsTransplanted(event)
            is NestRelocationEvent.OldNestCodeChanged -> updateOldNestCode(event)
            is NestRelocationEvent.ReasonForRelocationSelected -> updateReasonForRelocation(event)
            is NestRelocationEvent.RelocatedToSelected -> updateRelocatedTo(event)
            is NestRelocationEvent.SectorSelected -> updateSector(event)
            is NestRelocationEvent.SubsectorSelected -> updateSubsector(event)
            NestRelocationEvent.SaveButtonClicked -> updateStatus()
            is NestRelocationEvent.CommentsOrRemarksChanged -> updateCommentsOrRemarks(event)
        }
    }

    //private methods for updating the state

    private fun updateOldNestCode(event: NestRelocationEvent.OldNestCodeChanged) {
        state.update { previous ->
            previous.copy(
                oldNestCode = event.oldNestCode,
                invalidOldNestCodeMessage = validator.validateNonEmptyField(event.oldNestCode).message
            )
        }
    }

    private fun updateRelocatedTo(event: NestRelocationEvent.RelocatedToSelected) {
        state.update { previous ->
            previous.copy(
                relocatedTo = event.relocatedTo,
                invalidRelocatedToMessage = validator.validateNonEmptyField(event.relocatedTo).message
            )
        }
    }

    private fun updateSector(event: NestRelocationEvent.SectorSelected) {
        state.update { previous ->
            previous.copy(
                sector = event.sector,
                invalidSectorMessage = validator.validateNonEmptyField(event.sector).message
            )
        }
    }

    private fun updateSubsector(event: NestRelocationEvent.SubsectorSelected) {
        state.update { previous ->
            previous.copy(
                subsector = event.subsector,
                invalidSubsectorMessage = validator.validateNonEmptyField(event.subsector).message
            )
        }
    }

    private fun updateReasonForRelocation(event: NestRelocationEvent.ReasonForRelocationSelected) {
        state.update { previous ->
            previous.copy(
                reasonForRelocation = event.reasonForRelocation,
                invalidReasonForRelocationMessage = validator.validateNonEmptyField(event.reasonForRelocation).message
            )
        }
    }

    private fun updateNewNestCode(event: NestRelocationEvent.NewNestCodeChanged) {
        state.update { previous ->
            previous.copy(
                newNestCode = event.newNestCode,
                invalidNewNestCodeMessage = validator.validateNonEmptyField(event.newNestCode).message
            )
        }
    }

    private fun updateDepthTopEgg(event: NestRelocationEvent.DepthTopEggChanged) {
        state.update { previous ->
            previous.copy(
                depthTopEgg = event.depthTopEgg,
                invalidDepthTopEggMessage = validator.validateNumericText(event.depthTopEgg).message
            )
        }
    }

    private fun updateDepthBottomNest(event: NestRelocationEvent.DepthToBottomNestChanged) {
        state.update { previous ->
            previous.copy(
                depthBottomNest = event.depthToBottomNest,
                invalidDepthBottomNestMessage = validator.validateNumericText(event.depthToBottomNest).message
            )
        }
    }

    private fun updateDistanceToSea(event: NestRelocationEvent.DistanceToSeaChanged) {
        state.update { previous ->
            previous.copy(
                distanceToSea = event.distanceToSea,
                invalidDistanceToSeaMessage = validator.validateNumericText(event.distanceToSea).message
            )
        }
    }

    private fun updateNumEggsTransplanted(event: NestRelocationEvent.NumOfEggsTransplantedChanged) {
        state.update { previous ->
            previous.copy(
                numOfEggsTransplanted = event.numOfEggsTransplanted,
                invalidNumOfEggsTransplanted = validator.validateIntegerText(event.numOfEggsTransplanted).message
            )
        }
    }

    private fun updateCommentsOrRemarks(event: NestRelocationEvent.CommentsOrRemarksChanged) {
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