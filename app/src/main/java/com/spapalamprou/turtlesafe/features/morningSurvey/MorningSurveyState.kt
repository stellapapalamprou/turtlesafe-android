package com.spapalamprou.turtlesafe.features.morningSurvey

import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel
import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationState
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingState
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationState
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationState
import com.spapalamprou.turtlesafe.features.newNest.NewNestState

/**
 * Data class representing the state of the morning survey screen.
 *
 * @param date The date when the survey is conducted.
 * @param invalidDateMessage A message indicating an invalid date.
 * @param time The time when the survey is conducted.
 * @param invalidTimeMessage A message indicating an invalid time.
 * @param area The area where the survey is conducted.
 * @param invalidAreaMessage A message indicating an invalid area input.
 * @param beach The beach where the survey is conducted.
 * @param invalidBeachMessage A message indicating an invalid beach input.
 * @param newNestList A list of newly added nests.
 * @param sector The sector where the survey is conducted.
 * @param invalidSectorMessage A message indicating an invalid sector input.
 * @param subsector The subsector where the survey is conducted.
 * @param invalidSubsectorMessage A message indicating an invalid subsector input.
 * @param nestCode The selected nest code.
 * @param nestCodeOptions A list of available nest codes.
 * @param invalidNestCodeMessage A message indicating an invalid nest code.
 * @param nestIncubationList A list of nest incubations.
 * @param nestRelocationList A list of nest relocations.
 * @param nestHatchingList A list of nest hatchings.
 * @param nestExcavationList A list of nest excavations.
 * @param nestingAttemptSwitch A Boolean indicating whether the nesting attempt switch is checked.
 * @param numberOfAttempts The number of nesting attempts.
 * @param invalidNumberOfAttemptsMessage A message indicating an invalid number of attempts.
 * @param commentsOrRemarks Any additional comments or remarks for the survey.
 * @param invalidCommentsOrRemarksMessage A message indicating invalid comments or remarks.
 * @property success Indicates whether the night survey entry can be submitted.
 * @property exception Any exception that might be thrown.
 * @property nestCodeException An exception related to nest codes.
 */
data class MorningSurveyState(
    val date: String = "",
    val invalidDateMessage: String = "",
    val time: String = "",
    val invalidTimeMessage: String = "",
    val area: String = "",
    val invalidAreaMessage: String = "",
    val beach: String = "",
    val invalidBeachMessage: String = "",
    val newNestList: MutableList<NewNestState> = mutableListOf(),
    val sector: String = "",
    val invalidSectorMessage: String = "",
    val subsector: String = "",
    val invalidSubsectorMessage: String = "",
    val nestCode: String = "",
    val nestCodeOptions: List<String> = listOf(),
    val invalidNestCodeMessage: String = "",
    val nestIncubationList: MutableList<NestIncubationState> = mutableListOf(),
    val nestRelocationList: MutableList<NestRelocationState> = mutableListOf(),
    val nestHatchingList: MutableList<NestHatchingState> = mutableListOf(),
    val nestExcavationList: MutableList<NestExcavationState> = mutableListOf(),
    val nestingAttemptSwitch: Boolean = false,
    val numberOfAttempts: String = "",
    val invalidNumberOfAttemptsMessage: String = "",
    val commentsOrRemarks: String = "",
    val invalidCommentsOrRemarksMessage: String = "",
    val success: Boolean = false,
    val exception: Exception? = null,
    val nestCodeException: Exception? = null,
) {

    /**
     * Transforms the current state into a MorningSurveyModel.
     *
     * @return A MorningSurveyModel object containing the transformed data.
     */
    fun transformData(): MorningSurveyModel {
        return MorningSurveyModel(
            date = date,
            time = time,
            area = area,
            beach = beach,
            newNestList = newNestList.map { item ->
                item.transformData()
            },
            nestIncubationList = nestIncubationList.map { item ->
                item.transformData()
            },
            nestRelocationList = nestRelocationList.map { item ->
                item.transformData()
            },
            nestHatchingList = nestHatchingList.map { item ->
                item.transformData()
            },
            nestExcavationList = nestExcavationList.map { item ->
                item.transformData()
            },
            nestingAttemptSwitch = nestingAttemptSwitch,
            numberOfAttempts = if (nestingAttemptSwitch) {
                numberOfAttempts.toInt()
            } else null,
            commentsOrRemarks = commentsOrRemarks
        )
    }
}