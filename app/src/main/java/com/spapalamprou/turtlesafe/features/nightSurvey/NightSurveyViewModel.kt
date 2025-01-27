package com.spapalamprou.turtlesafe.features.nightSurvey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spapalamprou.turtlesafe.domain.useCases.NightSurveyUseCase
import com.spapalamprou.turtlesafe.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the state of the night survey screen.
 *
 * @property useCase An instance of NightSurveyUseCase that contains the business logic for the night survey.
 * @property validator An instance of Validator used for input validation.
 * @property state A MutableStateFlow representing the current state of the night survey screen.
 */
@HiltViewModel
class NightSurveyViewModel @Inject constructor(
    val useCase: NightSurveyUseCase,
    val validator: Validator
) : ViewModel() {

    val state: MutableStateFlow<NightSurveyState> = MutableStateFlow(NightSurveyState())

    /**
     * Sends an event to update the state based on user interactions.
     *
     * @param event The NightSurveyEvent that describes the user action.
     */
    fun sendEvent(event: NightSurveyEvent) {
        when (event) {
            NightSurveyEvent.AddNewTagButtonClicked -> addNewTag()
            NightSurveyEvent.AddOldTagButtonClicked -> addOldTag()
            is NightSurveyEvent.TurtleSafeTagSwitchChecked -> updateTurtleSafeTagSwitch(event)
            is NightSurveyEvent.AreaSelected -> updateArea(event)
            is NightSurveyEvent.BeachChanged -> updateBeach(event)
            is NightSurveyEvent.CommentsRemarksChanged -> updateCommentsRemarks(event)
            is NightSurveyEvent.CurvedCarapaceLengthChanged -> updateCrvCarapaceLength(event)
            is NightSurveyEvent.CurvedCarapaceWidthChanged -> updateCrvCarapaceWidth(event)
            is NightSurveyEvent.DamageCarapaceChanged -> updateDamageCarapace(event)
            is NightSurveyEvent.DamageFlippersHeadChanged -> updateDamageFlippersHead(event)
            is NightSurveyEvent.DateSelected -> updateDate(event)
            is NightSurveyEvent.DepartNestSiteSelected -> updateDepartNestSite(event)
            is NightSurveyEvent.DepthToBottomNestsChanged -> updateDepthBottomNest(event)
            is NightSurveyEvent.DepthTopEggChanged -> updateDepthTopEgg(event)
            is NightSurveyEvent.DistanceToSeaChanged -> updateDistanceToSea(event)
            is NightSurveyEvent.LostTagScarsChanged -> updateLostTagScars(event)
            is NightSurveyEvent.NestCodeChanged -> updateNestCode(event)
            is NightSurveyEvent.NestingEmergenceSwitchChecked -> updateNestingEmergence(event)
            is NightSurveyEvent.NewScarLocationChanged -> updateNewScarLocation(event)
            is NightSurveyEvent.NewScarTypeSelected -> updateNewScarType(event)
            is NightSurveyEvent.NewTagCodeChanged -> updateNewTagCode(event)
            is NightSurveyEvent.NewTagLocationSelected -> updateNewTagLocation(event)
            is NightSurveyEvent.NumOfEggsExcavatedChanged -> updateNumEggsExcavated(event)
            is NightSurveyEvent.NumOfEggsRelocatedChanged -> updateNumEggsRelocated(event)
            is NightSurveyEvent.OldTagCodeChanged -> updateOldTagCode(event)
            is NightSurveyEvent.OldTagLocationSelected -> updateOldTagLocation(event)
            is NightSurveyEvent.RelocationCommentsChanged -> updateRelocationComments(event)
            is NightSurveyEvent.SectorSelected -> updateSector(event)
            is NightSurveyEvent.StartCamouflageSelected -> updateStartCamouflage(event)
            is NightSurveyEvent.StartCoverSelected -> updateStartCover(event)
            is NightSurveyEvent.StartLayingSelected -> updateStartLaying(event)
            is NightSurveyEvent.StraightCarapaceLengthChanged -> updateStrCarapaceLength(event)
            is NightSurveyEvent.StraightCarapaceWidthChanged -> updateStrCarapaceWidth(event)
            NightSurveyEvent.SubmitButtonClicked -> sendData()
            is NightSurveyEvent.SubsectorSelected -> updateSubsector(event)
            is NightSurveyEvent.TagNotesChanged -> updateTagNotes(event)
            is NightSurveyEvent.TaggerChanged -> updateTagger(event)
            is NightSurveyEvent.TaggingSuccessfulSwitchChecked -> updateTaggingSuccessfulSwitch(
                event
            )

            is NightSurveyEvent.TurtleAtSeaSelected -> updateTurtleAtSea(event)
            is NightSurveyEvent.DeleteOldTagButtonClicked -> deleteOldTag(event)
            is NightSurveyEvent.DeleteNewTagButtonClicked -> deleteNewTag(event)
        }
    }

    private fun updateDate(event: NightSurveyEvent.DateSelected) {
        state.update { previous ->
            previous.copy(
                date = event.date,
                invalidDateMessage = validator.validateNonEmptyField(event.date).message
            )
        }
    }

    private fun updateArea(event: NightSurveyEvent.AreaSelected) {
        state.update { previous ->
            previous.copy(
                area = event.area,
                invalidAreaMessage = validator.validateNonEmptyField(event.area).message
            )
        }
    }

    private fun updateBeach(event: NightSurveyEvent.BeachChanged) {
        state.update { previous ->
            previous.copy(
                beach = event.beach,
                invalidBeachMessage = validator.validateName(event.beach).message
            )
        }
    }

    private fun updateSector(event: NightSurveyEvent.SectorSelected) {
        state.update { previous ->
            previous.copy(
                sector = event.sector,
                invalidSectorMessage = validator.validateNonEmptyField(event.sector).message
            )
        }
    }

    private fun updateSubsector(event: NightSurveyEvent.SubsectorSelected) {
        state.update { previous ->
            previous.copy(
                subsector = event.subsector,
                invalidSubsectorMessage = validator.validateNonEmptyField(event.subsector).message
            )
        }
    }

    private fun updateTagger(event: NightSurveyEvent.TaggerChanged) {
        state.update { previous ->
            previous.copy(
                tagger = event.tagger,
                invalidTaggerMessage = validator.validateName(event.tagger).message
            )
        }
    }

    private fun addOldTag() {
        state.update { previous ->
            val newList = previous.oldTagDataList.toMutableList()
            newList.add(OldTagData())

            previous.copy(
                oldTagDataList = newList
            )
        }
    }

    private fun deleteOldTag(event: NightSurveyEvent.DeleteOldTagButtonClicked) {
        state.update { previous ->
            val newList = previous.oldTagDataList.toMutableList()
            newList.removeAt(event.index)

            previous.copy(
                oldTagDataList = newList
            )
        }
    }

    private fun updateOldTagLocation(event: NightSurveyEvent.OldTagLocationSelected) {
        state.update { previous ->
            val oldTagEntry = previous.oldTagDataList[event.index]

            val newEntry = oldTagEntry.copy(
                selectedOldTagLocation = event.tagLocation,
                invalidOldTagLocationMessage = validator.validateNonEmptyField(event.tagLocation).message
            )
            val newList = previous.oldTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                oldTagDataList = newList
            )
        }
    }

    private fun updateOldTagCode(event: NightSurveyEvent.OldTagCodeChanged) {
        state.update { previous ->
            val oldTagEntry = previous.oldTagDataList[event.index]

            val newEntry = oldTagEntry.copy(
                oldTagCode = event.tagCode,
                invalidOldTagCodeMessage = validator.validateNonEmptyField(event.tagCode).message
            )
            val newList = previous.oldTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                oldTagDataList = newList
            )
        }
    }

    private fun updateTurtleSafeTagSwitch(event: NightSurveyEvent.TurtleSafeTagSwitchChecked) {
        state.update { previous ->
            val oldTagEntry = previous.oldTagDataList[event.index]

            val newEntry = oldTagEntry.copy(
                isTurtleSafeSwitchChecked = event.switch
            )
            val newList = previous.oldTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                oldTagDataList = newList
            )
        }
    }

    private fun updateTagNotes(event: NightSurveyEvent.TagNotesChanged) {
        state.update { previous ->
            val oldTagEntry = previous.oldTagDataList[event.index]

            val newEntry = oldTagEntry.copy(
                tagNotes = event.tagNotes,
                invalidTagNotesMessage = validator.validateNonEmptyField(event.tagNotes).message
            )
            val newList = previous.oldTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                oldTagDataList = newList
            )
        }
    }

    private fun addNewTag() {
        state.update { previous ->
            val newList = previous.newTagDataList.toMutableList()
            newList.add(NewTagData())

            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun deleteNewTag(event: NightSurveyEvent.DeleteNewTagButtonClicked) {
        state.update { previous ->
            val newList = previous.newTagDataList.toMutableList()
            newList.removeAt(event.index)

            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun updateNewTagLocation(event: NightSurveyEvent.NewTagLocationSelected) {
        state.update { previous ->
            val newTagEntry = previous.newTagDataList[event.index]

            val newEntry = newTagEntry.copy(
                selectedNewTagLocation = event.tagLocation,
                invalidNewTagLocationMessage = validator.validateNonEmptyField(event.tagLocation).message
            )
            val newList = previous.newTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun updateNewTagCode(event: NightSurveyEvent.NewTagCodeChanged) {
        state.update { previous ->
            val newTagEntry = previous.newTagDataList[event.index]

            val newEntry = newTagEntry.copy(
                newTagCode = event.tagCode,
                invalidNewTagCodeMessage = validator.validateNonEmptyField(event.tagCode).message
            )
            val newList = previous.newTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun updateTaggingSuccessfulSwitch(event: NightSurveyEvent.TaggingSuccessfulSwitchChecked) {
        state.update { previous ->
            val newTagEntry = previous.newTagDataList[event.index]

            val newEntry = newTagEntry.copy(
                isTagSwitchChecked = event.switch
            )
            val newList = previous.newTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun updateNewScarType(event: NightSurveyEvent.NewScarTypeSelected) {
        state.update { previous ->
            val newTagEntry = previous.newTagDataList[event.index]

            val newEntry = newTagEntry.copy(
                selectedNewScarType = event.scarType,
                invalidNewScarTypeMesssage = validator.validateNonEmptyField(event.scarType).message
            )
            val newList = previous.newTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun updateNewScarLocation(event: NightSurveyEvent.NewScarLocationChanged) {
        state.update { previous ->
            val newTagEntry = previous.newTagDataList[event.index]

            val newEntry = newTagEntry.copy(
                newScarLocation = event.scarLocation,
                invalidNewScarLocationMessage = validator.validateNonEmptyField(event.scarLocation).message
            )
            val newList = previous.newTagDataList.toMutableList()
            newList[event.index] = newEntry
            previous.copy(
                newTagDataList = newList
            )
        }
    }

    private fun updateLostTagScars(event: NightSurveyEvent.LostTagScarsChanged) {
        state.update { previous ->
            previous.copy(
                lostTagScars = event.lostTagScars
            )
        }
    }

    private fun updateStrCarapaceLength(event: NightSurveyEvent.StraightCarapaceLengthChanged) {
        state.update { previous ->
            previous.copy(
                straightCarapaceLength = event.strCarapaceLength,
                invalidStraightCarapaceLengthMessage = validator.validateNumericText(event.strCarapaceLength).message
            )
        }
    }

    private fun updateStrCarapaceWidth(event: NightSurveyEvent.StraightCarapaceWidthChanged) {
        state.update { previous ->
            previous.copy(
                straightCarapaceWidth = event.strCarapaceWidth,
                invalidStraightCarapaceWidthMessage = validator.validateNumericText(event.strCarapaceWidth).message
            )
        }
    }

    private fun updateCrvCarapaceLength(event: NightSurveyEvent.CurvedCarapaceLengthChanged) {
        state.update { previous ->
            previous.copy(
                curvedCarapaceLength = event.crvCarapaceLength,
                invalidCurvedCarapaceLengthMessage = validator.validateNumericText(event.crvCarapaceLength).message
            )
        }
    }

    private fun updateCrvCarapaceWidth(event: NightSurveyEvent.CurvedCarapaceWidthChanged) {
        state.update { previous ->
            previous.copy(
                curvedCarapaceWidth = event.crvCarapaceWidth,
                invalidCurvedCarapaceWidthMessage = validator.validateNumericText(event.crvCarapaceWidth).message
            )
        }
    }

    private fun updateDamageFlippersHead(event: NightSurveyEvent.DamageFlippersHeadChanged) {
        state.update { previous ->
            previous.copy(
                damageFlippersHead = event.damageFlippersHead
            )
        }
    }

    private fun updateDamageCarapace(event: NightSurveyEvent.DamageCarapaceChanged) {
        state.update { previous ->
            previous.copy(
                damageCarapace = event.damageCarapace
            )
        }
    }

    private fun updateNestingEmergence(event: NightSurveyEvent.NestingEmergenceSwitchChecked) {
        state.update { previous ->
            previous.copy(
                nestEmergenceSwitch = event.switch
            )
        }
    }

    private fun updateNestCode(event: NightSurveyEvent.NestCodeChanged) {
        state.update { previous ->
            previous.copy(
                nestCode = event.nestCode,
                invalidNestCodeMessage = validator.validateNonEmptyField(event.nestCode).message
            )
        }
    }

    private fun updateDepthTopEgg(event: NightSurveyEvent.DepthTopEggChanged) {
        state.update { previous ->
            previous.copy(
                depthTopEgg = event.depthTopEgg,
                invalidDepthTopEggMessage = validator.validateNumericText(event.depthTopEgg).message
            )
        }
    }

    private fun updateDepthBottomNest(event: NightSurveyEvent.DepthToBottomNestsChanged) {
        state.update { previous ->
            previous.copy(
                depthBottomNests = event.depthToBottomNest,
                invalidDepthBottomNestMessage = validator.validateNumericText(event.depthToBottomNest).message
            )
        }
    }

    private fun updateDistanceToSea(event: NightSurveyEvent.DistanceToSeaChanged) {
        state.update { previous ->
            previous.copy(
                distanceToSea = event.distanceToSea,
                invalidDistanceToSeaMessage = validator.validateNumericText(event.distanceToSea).message
            )
        }
    }

    private fun updateNumEggsRelocated(event: NightSurveyEvent.NumOfEggsRelocatedChanged) {
        state.update { previous ->
            previous.copy(
                numEggsRelocated = event.numOfEggsRelocated,
                invalidNumEggsRelocatedMessage = validator.validateIntegerText(event.numOfEggsRelocated).message
            )
        }
    }

    private fun updateRelocationComments(event: NightSurveyEvent.RelocationCommentsChanged) {
        state.update { previous ->
            previous.copy(
                relocationComments = event.relocationComments
            )
        }
    }

    private fun updateNumEggsExcavated(event: NightSurveyEvent.NumOfEggsExcavatedChanged) {
        state.update { previous ->
            previous.copy(
                numEggsExcavated = event.numOfEggsExcavated,
                invalidNumEggsExcavatedMessage = validator.validateIntegerText(event.numOfEggsExcavated).message
            )
        }
    }

    private fun updateStartLaying(event: NightSurveyEvent.StartLayingSelected) {
        state.update { previous ->
            previous.copy(
                startLaying = event.startLaying
            )
        }
    }

    private fun updateStartCover(event: NightSurveyEvent.StartCoverSelected) {
        state.update { previous ->
            previous.copy(
                startCover = event.startCover
            )
        }
    }

    private fun updateStartCamouflage(event: NightSurveyEvent.StartCamouflageSelected) {
        state.update { previous ->
            previous.copy(
                startCamouflage = event.startCamouflage
            )
        }
    }

    private fun updateDepartNestSite(event: NightSurveyEvent.DepartNestSiteSelected) {
        state.update { previous ->
            previous.copy(
                departNestSite = event.departNestSite
            )
        }
    }

    private fun updateTurtleAtSea(event: NightSurveyEvent.TurtleAtSeaSelected) {
        state.update { previous ->
            previous.copy(
                turtleAtSea = event.turtleAtSea
            )
        }
    }

    private fun updateCommentsRemarks(event: NightSurveyEvent.CommentsRemarksChanged) {
        state.update { previous ->
            previous.copy(
                commentsRemarks = event.commentsRemarks
            )
        }
    }

    private fun sendData() {
        viewModelScope.launch {
            try {
                useCase.submit(
                    nightSurvey = state.value.transformData()
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