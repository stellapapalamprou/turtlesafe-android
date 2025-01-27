package com.spapalamprou.turtlesafe.features.nestExcavation

import androidx.lifecycle.ViewModel
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for managing the state of the nest excavation screen.
 *
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the nest excavation screen.

 */
@HiltViewModel
class NestExcavationViewModel @Inject constructor(
    val validator: Validator
) : ViewModel() {
    val state: MutableStateFlow<NestExcavationState> = MutableStateFlow(NestExcavationState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The NestExcavationEvent that describes the user action.
     */
    fun sendEvent(event: NestExcavationEvent) {
        when (event) {
            is NestExcavationEvent.CommentsOrRemarksChanged -> updateCommentsOrRemarks(event)
            is NestExcavationEvent.DeadEmbryosChanged -> updateDeadEmbryos(event)
            is NestExcavationEvent.DeadHatchlingsNestChanged -> updateDeadHatchlingsNest(event)
            is NestExcavationEvent.HatchedEggsChanged -> updateHatchedEggs(event)
            is NestExcavationEvent.LiveEmbryosChanged -> updateLiveEmbryos(event)
            is NestExcavationEvent.LiveHatchlingsNestChanged -> updateLiveHatchlingsNest(event)
            is NestExcavationEvent.NestCodeChanged -> updateNestCode(event)
            is NestExcavationEvent.NoEmbryosChanged -> updateNoEmbryos(event)
            is NestExcavationEvent.PippedDeadHatchlingsChanged -> updatePippedDeadHatchlings(event)
            is NestExcavationEvent.PippedLiveHatchlingsChanged -> updatePippedLiveHatchlings(event)
            NestExcavationEvent.SaveButtonClicked -> updateStatus()
        }
    }

    private fun updateNestCode(event: NestExcavationEvent.NestCodeChanged) {
        state.update { previous ->
            previous.copy(
                nestCode = event.nestCode,
                invalidNestCodeMessage = validator.validateNonEmptyField(event.nestCode).message
            )
        }
    }

    private fun updateHatchedEggs(event: NestExcavationEvent.HatchedEggsChanged) {
        state.update { previous ->
            previous.copy(
                hatchedEggs = event.hatchedEggs,
                invalidHatchedEggsMessage = validator.validateIntegerText(event.hatchedEggs).message
            )
        }
    }

    private fun updatePippedDeadHatchlings(event: NestExcavationEvent.PippedDeadHatchlingsChanged) {
        state.update { previous ->
            previous.copy(
                pippedDeadHatchlings = event.pippedDeadHatchlings,
                invalidPippedDeadHatchlingsMessage = validator.validateIntegerText(event.pippedDeadHatchlings).message
            )
        }
    }

    private fun updatePippedLiveHatchlings(event: NestExcavationEvent.PippedLiveHatchlingsChanged) {
        state.update { previous ->
            previous.copy(
                pippedLiveHatchlings = event.pippedLiveHatchlings,
                invalidPippedLiveHatchlingsMessage = validator.validateIntegerText(event.pippedLiveHatchlings).message
            )
        }
    }

    private fun updateNoEmbryos(event: NestExcavationEvent.NoEmbryosChanged) {
        state.update { previous ->
            previous.copy(
                noEmbryos = event.noEmbryos,
                invalidNoEmbryosMessage = validator.validateIntegerText(event.noEmbryos).message
            )
        }
    }

    private fun updateDeadEmbryos(event: NestExcavationEvent.DeadEmbryosChanged) {
        state.update { previous ->
            previous.copy(
                deadEmbryos = event.deadEmbryos,
                invalidDeadEmbryosMessage = validator.validateIntegerText(event.deadEmbryos).message
            )
        }
    }

    private fun updateLiveEmbryos(event: NestExcavationEvent.LiveEmbryosChanged) {
        state.update { previous ->
            previous.copy(
                liveEmbryos = event.liveEmbryos,
                invalidLiveEmbryosMessage = validator.validateIntegerText(event.liveEmbryos).message
            )
        }
    }

    private fun updateDeadHatchlingsNest(event: NestExcavationEvent.DeadHatchlingsNestChanged) {
        state.update { previous ->
            previous.copy(
                deadHatchlingsNest = event.deadHatchlingsNest,
                invalidDeadHatchlingsNestMessage = validator.validateIntegerText(event.deadHatchlingsNest).message
            )
        }
    }

    private fun updateLiveHatchlingsNest(event: NestExcavationEvent.LiveHatchlingsNestChanged) {
        state.update { previous ->
            previous.copy(
                liveHatchlingsNest = event.liveHatchlingsNest,
                invalidLiveHatchlingsNestMessage = validator.validateIntegerText(event.liveHatchlingsNest).message
            )
        }
    }

    private fun updateCommentsOrRemarks(event: NestExcavationEvent.CommentsOrRemarksChanged) {
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