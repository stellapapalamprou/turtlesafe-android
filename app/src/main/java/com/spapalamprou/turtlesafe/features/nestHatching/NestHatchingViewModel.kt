package com.spapalamprou.turtlesafe.features.nestHatching

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for managing the state of the nest hatching screen.
 *
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the nest hatching screen.
 */
@HiltViewModel
class NestHatchingViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {

    val state: MutableStateFlow<NestHatchingState> = MutableStateFlow(NestHatchingState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The NestHatchingEvent that describes the user action.
     */
    fun sendEvent(event: NestHatchingEvent) {
        when (event) {
            is NestHatchingEvent.CommentsOrRemarksChanged -> updateCommentsOrRemarks(event)
            is NestHatchingEvent.DeleteHatchEventButtonClicked -> deleteHatchingEvent(event)
            is NestHatchingEvent.FirstDayOfHatchingSelected -> updateFirstDayOfHatching(event)
            NestHatchingEvent.HatchEventAddButtonClicked -> addHatchingEvent()
            is NestHatchingEvent.HatchEventTypeSelected -> updateHatchingEvent(event)
            is NestHatchingEvent.LastDayOfHatchingSelected -> updateLastDayOfHatching(event)
            NestHatchingEvent.SaveButtonClicked -> updateStatus()
            is NestHatchingEvent.NestCodeChanged -> updateNestCode(event)
        }
    }

    private fun updateNestCode(event: NestHatchingEvent.NestCodeChanged) {
        state.update { previous ->
            previous.copy(
                nestCode = event.nestCode,
                invalidNestCodeMessage = validator.validateNonEmptyField(event.nestCode).message
            )
        }
    }

    private fun updateCommentsOrRemarks(event: NestHatchingEvent.CommentsOrRemarksChanged) {
        state.update { previous ->
            previous.copy(
                commentsOrRemarks = event.commentsOrRemarks
            )
        }
    }

    private fun updateFirstDayOfHatching(event: NestHatchingEvent.FirstDayOfHatchingSelected) {
        state.update { previous ->
            previous.copy(
                firstDayHatching = event.date,
                invalidFirstDayHatchingMessage = validator.validateNonEmptyField(event.date).message
            )
        }
    }

    private fun updateLastDayOfHatching(event: NestHatchingEvent.LastDayOfHatchingSelected) {
        state.update { previous ->
            previous.copy(
                lastDayHatching = event.date,
                invalidLastDayHatchingMessage = validator.validateNonEmptyField(event.date).message
            )
        }
    }

    private fun addHatchingEvent() {
        state.update { previous ->
            val newList = previous.hatchingDataList.toMutableList()
            newList.add(HatchingData())

            previous.copy(
                hatchingDataList = newList
            )
        }
    }

    private fun deleteHatchingEvent(event: NestHatchingEvent.DeleteHatchEventButtonClicked) {
        state.update { previous ->
            val newList = previous.hatchingDataList.toMutableList()
            newList.removeAt(event.index)

            previous.copy(
                hatchingDataList = newList
            )

        }
    }

    private fun updateHatchingEvent(event: NestHatchingEvent.HatchEventTypeSelected) {
        state.update { previous ->
            val hatchEntry = previous.hatchingDataList[event.index]

            val updatedEntry = hatchEntry.copy(
                selectedHatchingEvent = event.eventType
            )
            val newList = previous.hatchingDataList.toMutableList()
            newList[event.index] = updatedEntry

            previous.copy(
                hatchingDataList = newList
            )
        }
    }

    private fun updateStatus() {
        state.update { previous ->
            previous.copy(success = true)
        }
    }
}