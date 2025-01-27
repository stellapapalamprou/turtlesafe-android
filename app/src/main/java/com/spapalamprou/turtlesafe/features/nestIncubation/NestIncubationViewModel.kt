package com.spapalamprou.turtlesafe.features.nestIncubation

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for managing the state of the nest incubation screen.
 *
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the nest incubation screen.
 */
@HiltViewModel
class NestIncubationViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {
    val state: MutableStateFlow<NestIncubationState> = MutableStateFlow(NestIncubationState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The NestIncubationEvent that describes the user action.
     */
    fun sendEvent(event: NestIncubationEvent) {
        when (event) {
            is NestIncubationEvent.CommentsOrRemarksChanged -> updateCommentsOrRemarks(event)
            is NestIncubationEvent.HatcheryCodeChanged -> updateHatcheryCode(event)
            NestIncubationEvent.IncEventAddButtonClicked -> addIncubationEvent()
            is NestIncubationEvent.IncEventTypeSelected -> updateIncubationEvent(event)
            is NestIncubationEvent.NestCodeChanged -> updateNestCode(event)
            is NestIncubationEvent.NestLocationSelected -> updateNestLocation(event)
            NestIncubationEvent.SaveButtonClicked -> updateStatus()
            is NestIncubationEvent.DeleteIncEventButtonClicked -> deleteIncubationEvent(event)
            is NestIncubationEvent.ProtectedNestSwitchChecked -> updateProtectedNestSwitch(event)
            is NestIncubationEvent.ProtectionMeasuresSelected -> updateProtectionMeasures(event)
        }
    }

    private fun updateNestCode(event: NestIncubationEvent.NestCodeChanged) {
        state.update { previous ->
            previous.copy(
                nestCode = event.nestCode,
                invalidNestCodeMessage = validator.validateNonEmptyField(event.nestCode).message
            )
        }
    }

    private fun updateNestLocation(event: NestIncubationEvent.NestLocationSelected) {
        state.update { previous ->
            previous.copy(
                nestLocation = event.nestLocation,
                invalidNestLocationMessage = validator.validateNonEmptyField(event.nestLocation).message
            )
        }
    }

    private fun updateHatcheryCode(event: NestIncubationEvent.HatcheryCodeChanged) {
        state.update { previous ->
            previous.copy(
                hatcheryCode = event.hatcheryCode,
                invalidHatcheryCodeMessage = validator.validateNonEmptyField(event.hatcheryCode).message
            )
        }
    }

    private fun addIncubationEvent() {
        state.update { previous ->
            val newList = previous.incubationDataList.toMutableList()
            newList.add(IncubationData())

            previous.copy(
                incubationDataList = newList
            )
        }
    }

    private fun deleteIncubationEvent(event: NestIncubationEvent.DeleteIncEventButtonClicked) {
        state.update { previous ->
            val newList = previous.incubationDataList.toMutableList()
            newList.removeAt(event.index)

            previous.copy(
                incubationDataList = newList
            )
        }
    }

    private fun updateIncubationEvent(event: NestIncubationEvent.IncEventTypeSelected) {
        state.update { previous ->
            val incEntry = previous.incubationDataList[event.index]

            val updatedEntry = incEntry.copy(
                selectedIncubationEvent = event.eventType,
                invalidIncEventMessage = validator.validateNonEmptyField(event.eventType).message
            )
            val newList = previous.incubationDataList.toMutableList()
            newList[event.index] = updatedEntry

            previous.copy(
                incubationDataList = newList
            )
        }
    }

    private fun updateProtectedNestSwitch(event: NestIncubationEvent.ProtectedNestSwitchChecked) {
        state.update { previous ->
            previous.copy(
                protectedNestSwitch = event.protectedNestSwitch
            )
        }
    }

    private fun updateProtectionMeasures(event: NestIncubationEvent.ProtectionMeasuresSelected) {
        state.update { previous ->
            previous.copy(
                protectionMeasures = event.protectionMeasures,
                invalidProtectionMeasuresMessage = validator.validateNonEmptyField(event.protectionMeasures).message
            )
        }
    }

    private fun updateCommentsOrRemarks(event: NestIncubationEvent.CommentsOrRemarksChanged) {
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