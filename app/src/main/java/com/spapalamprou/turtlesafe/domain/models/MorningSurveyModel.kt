package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a morning survey.
 *
 * @property date The date when the morning survey was conducted.
 * @property time The time when the morning survey was conducted.
 * @property area The area where the survey took place.
 * @property beach The beach where the survey was conducted.
 * @property newNestList A list of new nests recorded during the survey.
 * @property nestIncubationList A list of incubation details recorded for existing nests during the survey.
 * @property nestRelocationList A list of relocation details recorded for existing nests during the survey.
 * @property nestHatchingList A list of hatching details recorded for existing nests during the survey.
 * @property nestExcavationList A list of excavation details recorded for existing nests during the survey.
 * @property nestingAttemptSwitch A boolean indicating whether nesting attempts were observed.
 * @property numberOfAttempts The number of nesting attempts observed.
 * @property commentsOrRemarks Any additional comments or remarks related to the survey.
 */
data class MorningSurveyModel(
    val date: String,
    val time: String,
    val area: String,
    val beach: String,
    val newNestList: List<NewNestModel>,
    val nestIncubationList: List<NestIncubationModel>,
    val nestRelocationList: List<NestRelocationModel>,
    val nestHatchingList: List<NestHatchingModel>,
    val nestExcavationList: List<NestExcavationModel>,
    val nestingAttemptSwitch: Boolean,
    val numberOfAttempts: Int?,
    val commentsOrRemarks: String
)