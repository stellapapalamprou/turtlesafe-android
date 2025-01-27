package com.spapalamprou.turtlesafe.features.morningSurvey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spapalamprou.turtlesafe.domain.useCases.GetNestCodeUseCase
import com.spapalamprou.turtlesafe.domain.useCases.MorningSurveyUseCase
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the state of the morning survey screen.
 *
 * @property useCase An instance of MorningSurveyUseCase that handles the business logic related to the morning survey.
 * @property getNestCodeUseCase An instance of GetNestCodeUseCase that retrieved the available nest codes.
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the morning survey screen.
 */
@HiltViewModel
class MorningSurveyViewModel @Inject constructor(
    val useCase: MorningSurveyUseCase,
    val getNestCodeUseCase: GetNestCodeUseCase,
    val validator: Validator
) : ViewModel() {
    val state: MutableStateFlow<MorningSurveyState> = MutableStateFlow(MorningSurveyState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The MorningSurveyEvent that describes the user action.
     */
    fun sendEvent(event: MorningSurveyEvent) {
        when (event) {
            is MorningSurveyEvent.AreaSelected -> updateArea(event)
            is MorningSurveyEvent.BeachChanged -> updateBeach(event)
            is MorningSurveyEvent.CommentsOrRemarksChanged -> updateCommentsOrRemarks(event)
            is MorningSurveyEvent.DateSelected -> updateDate(event)
            is MorningSurveyEvent.NestCodeSelected -> updateNestCode(event)
            is MorningSurveyEvent.NestingAttemptSwitchChecked -> updateNestSwitch(event)
            is MorningSurveyEvent.NumberOfAttemptsChanged -> updateNumberOfAttempts(event)
            is MorningSurveyEvent.SectorSelected -> updateSector(event)
            MorningSurveyEvent.SubmitButtonClicked -> sendData()
            is MorningSurveyEvent.SubsectorSelected -> updateSubector(event)
            is MorningSurveyEvent.TimeSelected -> updateTime(event)
            is MorningSurveyEvent.NewNestAdded -> addNewNest(event)
            is MorningSurveyEvent.NestRelocationAdded -> addNestRelocation(event)
            is MorningSurveyEvent.NestExcavationAdded -> addNestExcavation(event)
            is MorningSurveyEvent.NestHatchingAdded -> addNestHatching(event)
            is MorningSurveyEvent.NestIncubationAdded -> addNestIncubation(event)
        }
    }

    private fun updateArea(event: MorningSurveyEvent.AreaSelected) {
        state.update { previous ->
            previous.copy(
                area = event.area,
                invalidAreaMessage = validator.validateNonEmptyField(event.area).message
            )
        }
        this.updateNestCodeOptions()
    }

    private fun updateBeach(event: MorningSurveyEvent.BeachChanged) {
        state.update { previous ->
            previous.copy(
                beach = event.beach,
                invalidBeachMessage = validator.validateName(event.beach).message
            )
        }
        this.updateNestCodeOptions()
    }

    private fun updateCommentsOrRemarks(event: MorningSurveyEvent.CommentsOrRemarksChanged) {
        state.update { previous ->
            previous.copy(
                commentsOrRemarks = event.commentsOrRemarks
            )
        }
    }

    private fun updateDate(event: MorningSurveyEvent.DateSelected) {
        state.update { previous ->
            previous.copy(
                date = event.date,
                invalidDateMessage = validator.validateNonEmptyField(event.date).message
            )
        }
    }

    private fun updateNestCode(event: MorningSurveyEvent.NestCodeSelected) {
        state.update { previous ->
            previous.copy(
                nestCode = event.nestCode,
                invalidNestCodeMessage = validator.validateNonEmptyField(event.nestCode).message
            )
        }
    }

    private fun updateNestSwitch(event: MorningSurveyEvent.NestingAttemptSwitchChecked) {
        state.update { previous ->
            previous.copy(
                nestingAttemptSwitch = event.switch
            )
        }
    }

    private fun updateNumberOfAttempts(event: MorningSurveyEvent.NumberOfAttemptsChanged) {
        state.update { previous ->
            previous.copy(
                numberOfAttempts = event.numberOfAttempts,
                invalidNumberOfAttemptsMessage = validator.validateIntegerText(event.numberOfAttempts).message
            )
        }
    }

    private fun updateSector(event: MorningSurveyEvent.SectorSelected) {
        state.update { previous ->
            previous.copy(
                sector = event.sector,
                invalidSectorMessage = validator.validateNonEmptyField(event.sector).message
            )
        }
        this.updateNestCodeOptions()
    }

    private fun updateSubector(event: MorningSurveyEvent.SubsectorSelected) {
        state.update { previous ->
            previous.copy(
                subsector = event.subsector,
                invalidSubsectorMessage = validator.validateNonEmptyField(event.subsector).message
            )
        }
        this.updateNestCodeOptions()
    }

    private fun updateTime(event: MorningSurveyEvent.TimeSelected) {
        state.update { previous ->
            previous.copy(
                time = event.time,
                invalidTimeMessage = validator.validateNonEmptyField(event.time).message
            )
        }
    }

    private fun addNewNest(event: MorningSurveyEvent.NewNestAdded) {
        state.update { previous ->
            val newList = previous.newNestList.toMutableList()
            newList.add(event.newNest)

            previous.copy(
                newNestList = newList
            )
        }
    }

    private fun addNestRelocation(event: MorningSurveyEvent.NestRelocationAdded) {
        state.update { previous ->
            val newList = previous.nestRelocationList.toMutableList()
            newList.add(event.nestRelocation)

            previous.copy(
                nestRelocationList = newList
            )
        }
    }

    private fun addNestIncubation(event: MorningSurveyEvent.NestIncubationAdded) {
        state.update { previous ->
            val newList = previous.nestIncubationList.toMutableList()
            newList.add(event.nestIncubation)

            previous.copy(
                nestIncubationList = newList
            )
        }
    }

    private fun addNestExcavation(event: MorningSurveyEvent.NestExcavationAdded) {
        state.update { previous ->
            val newList = previous.nestExcavationList.toMutableList()
            newList.add(event.nestExcavation)

            previous.copy(
                nestExcavationList = newList
            )
        }
    }

    private fun addNestHatching(event: MorningSurveyEvent.NestHatchingAdded) {
        state.update { previous ->
            val newList = previous.nestHatchingList.toMutableList()
            newList.add(event.nestHatching)

            previous.copy(
                nestHatchingList = newList
            )
        }
    }

    private fun updateNestCodeOptions() {
        viewModelScope.launch {
            try {
                val nestCodeOptions: List<String> = getNestCodeUseCase.getNestCode(
                    area = state.value.area,
                    beach = state.value.beach,
                    sector = state.value.sector,
                    subsector = state.value.subsector
                )
                state.update { previous ->
                    previous.copy(
                        nestCodeOptions = nestCodeOptions,
                        nestCode = ""
                    )
                }
            } catch (exception: Exception) {
                state.update { previous -> previous.copy(nestCodeException = exception) }
            }
        }
    }

    private fun sendData() {
        viewModelScope.launch {
            try {
                useCase.submit(
                    morningSurvey = state.value.transformData()
                )
                state.update { previous ->
                    previous.copy(success = true)
                }
            } catch (exception: Exception) {
                state.update { previous -> previous.copy(exception = exception) }
            }
        }
    }
}