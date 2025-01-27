package com.spapalamprou.turtlesafe.features.morningSurvey

import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationState
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingState
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationState
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationState
import com.spapalamprou.turtlesafe.features.newNest.NewNestState

/**
 * Sealed class representing the various events that can occur in the morning survey screen.
 */
sealed class MorningSurveyEvent {

    /**
     * Event triggered when a date is selected in the morning survey.
     *
     * @param date The selected date as a String.
     */
    data class DateSelected(
        val date: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when a time is selected in the morning survey.
     *
     * @param time The selected time as a String.
     */
    data class TimeSelected(
        val time: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when an area is selected.
     *
     * @param area The selected area.
     */
    data class AreaSelected(
        val area: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when the beach is selected.
     *
     * @param beach The name of the beach.
     */
    data class BeachChanged(
        val beach: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when a new nest is added in the morning survey.
     *
     * @param newNest The state of the newly added nest.
     */
    data class NewNestAdded(
        val newNest: NewNestState
    ): MorningSurveyEvent()

    /**
     * Event triggered when a sector is selected.
     *
     * @param sector The selected sector.
     */
    data class SectorSelected(
        val sector: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when a subsector is selected.
     *
     * @param subsector The selected subsector.
     */
    data class SubsectorSelected(
        val subsector: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when a nest code is selected.
     *
     * @param nestCode The selected nest code.
     */
    data class NestCodeSelected(
        val nestCode: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when a nest relocation is added in the morning survey.
     *
     * @param nestRelocation The state of the nest relocation added.
     */
    data class NestRelocationAdded(
        val nestRelocation: NestRelocationState
    ): MorningSurveyEvent()

    /**
     * Event triggered when a nest incubation is added in the morning survey.
     *
     * @param nestIncubation The state of the nest incubation added.
     */
    data class NestIncubationAdded(
        val nestIncubation: NestIncubationState
    ): MorningSurveyEvent()

    /**
     * Event triggered when a nest excavation is added in the morning survey.
     *
     * @param nestExcavation The state of the nest excavation added.
     */
    data class NestExcavationAdded(
        val nestExcavation: NestExcavationState
    ): MorningSurveyEvent()

    /**
     * Event triggered when a nest hatching is added in the morning survey.
     *
     * @param nestHatching The state of the nest hatching added.
     */
    data class NestHatchingAdded(
        val nestHatching: NestHatchingState
    ): MorningSurveyEvent()

    /**
     * Event triggered when the nesting attempt switch changes.
     *
     * @param switch A Boolean indicating whether the nesting attempt switch is checked.
     */
    data class NestingAttemptSwitchChecked(
        val switch: Boolean
    ) : MorningSurveyEvent()

    /**
     * Event triggered when the number of nesting attempts is changed.
     *
     * @param numberOfAttempts The number of nesting attempts.
     */
    data class NumberOfAttemptsChanged(
        val numberOfAttempts: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when comments or remarks are entered.
     *
     * @param commentsOrRemarks The comments or remarks entered.
     */
    data class CommentsOrRemarksChanged(
        val commentsOrRemarks: String
    ) : MorningSurveyEvent()

    /**
     * Event triggered when the submit button is clicked.
     */
    object SubmitButtonClicked : MorningSurveyEvent()
}