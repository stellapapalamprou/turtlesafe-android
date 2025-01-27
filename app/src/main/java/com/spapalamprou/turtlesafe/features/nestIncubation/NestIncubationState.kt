package com.spapalamprou.turtlesafe.features.nestIncubation

import com.spapalamprou.turtlesafe.domain.models.IncubationDataModel
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel

/**
 * Data class representing the state of a nest incubation screen.
 *
 * @property nestCode The unique identifier for the nest.
 * @property invalidNestCodeMessage Message indicating if the nest code is invalid.
 * @property nestLocation The location of the incubating nest.
 * @property invalidNestLocationMessage Message indicating if the nest location is invalid.
 * @property hatcheryCode The code of the hatchery where the nest is incubating.
 * @property invalidHatcheryCodeMessage Message indicating if the hatchery code is invalid.
 * @property incubationDataList A list containing data about individual incubation events.
 * @property protectedNestSwitch Boolean indicating whether the nest is protected.
 * @property protectionMeasures Measures taken to protect the nest.
 * @property invalidProtectionMeasuresMessage Message indicating if the input of protection measures is invalid.
 * @property commentsOrRemarks Additional comments or remarks related to the nest.
 * @property invalidCommentsOrRemarksMessage Message indicating the validity of the comments or remarks.
 * @property success Indicates whether the incubation entry can be saved.
 */
data class NestIncubationState(
    val nestCode: String = "",
    val invalidNestCodeMessage: String = "",
    val nestLocation: String = "",
    val invalidNestLocationMessage: String = "",
    val hatcheryCode: String = "",
    val invalidHatcheryCodeMessage: String = "",
    val incubationDataList: MutableList<IncubationData> = mutableListOf(),
    val protectedNestSwitch: Boolean = false,
    val protectionMeasures: String = "",
    val invalidProtectionMeasuresMessage: String = "",
    val commentsOrRemarks: String = "",
    val invalidCommentsOrRemarksMessage: String = "",
    val success: Boolean = false

) {

    /**
     * Transforms the current state into a NestIncubationModel.
     *
     * @return A NestIncubationModel object containing the transformed data.
     */
    fun transformData(): NestIncubationModel {
        return NestIncubationModel(
            nestCode = nestCode,
            nestLocation = when (nestLocation) {
                "H: Hatchery" -> "H"
                "BoB: Back of Beach" -> "BoB"
                else -> ""
            },
            protectedNestSwitch = protectedNestSwitch,
            protectionMeasures = protectionMeasures,
            hatcheryCode = if (nestLocation.equals("H: Hatchery")) {
                hatcheryCode
            } else {
                null
            },
            incubationDataList = incubationDataList.map { item ->
                item.transformData()
            },
            commentsOrRemarks = commentsOrRemarks
        )
    }
}

/**
 * Data class representing the data associated with an incubation event.
 *
 * @property selectedIncubationEvent The type of incubation event that has been selected by the user.
 * @property invalidIncEventMessage A message indicating whether the selected incubation event is valid.
 */
data class IncubationData(
    val selectedIncubationEvent: String = "",
    val invalidIncEventMessage: String = ""
) {

    /**
     * Transforms the current state into a NestIncubationDataModel.
     *
     * @return A NestIncubationDataModel object containing the transformed data.
     */
    fun transformData(): IncubationDataModel {
        return IncubationDataModel(
            selectedIncubationEvent = when (selectedIncubationEvent) {
                "A: Predation Attempt" -> "A"
                "E: Emergence" -> "E"
                "I: Inundation" -> "I"
                "P: Predation" -> "P"
                "V: Vandalism" -> "V"
                "W: Washed Nest" -> "W"
                "O: Other" -> "O"
                else -> ""
            }
        )
    }
}