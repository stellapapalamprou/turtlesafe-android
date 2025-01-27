package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a nest incubation.
 *
 * @property nestCode A code representing the specific nest being incubated.
 * @property nestLocation The location of the nest.
 * @property hatcheryCode A code representing a hatchery, in case the nest is incubating in a hatchery.
 * @property incubationDataList a list on incubation events.
 * @property protectedNestSwitch A boolean indicating whether the nest is protected or not.
 * @property protectionMeasures A description of the protection measures placed for the nest.
 * @property commentsOrRemarks Any additional comments or remarks regarding the incubation.
 */
data class NestIncubationModel(
    val nestCode: String,
    val nestLocation: String,
    val hatcheryCode: String?,
    val incubationDataList: List<IncubationDataModel>,
    val protectedNestSwitch: Boolean,
    val protectionMeasures: String,
    val commentsOrRemarks: String
)

/**
 * Model representing an incubation event.
 *
 * @property selectedIncubationEvent The incubation event selected.
 */
data class IncubationDataModel(
    val selectedIncubationEvent: String
)