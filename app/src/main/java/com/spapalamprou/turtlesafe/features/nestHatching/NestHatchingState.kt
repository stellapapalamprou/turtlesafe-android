package com.spapalamprou.turtlesafe.features.nestHatching

import com.spapalamprou.turtlesafe.domain.models.HatchingDataModel
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel

/**
 * Data class representing the state of the nest hatching screen.
 *
 * @property nestCode A code representing the specific nest being hatched.
 * @property invalidNestCodeMessage A message indicating if the nest code is invalid.
 * @property firstDayHatching The date when hatching first occurred.
 * @property invalidFirstDayHatchingMessage A message indicating if the first day of hatching is invalid.
 * @property lastDayHatching The date when hatching last occurred.
 * @property invalidLastDayHatchingMessage A message indicating if the last day of hatching is invalid.
 * @property hatchingDataList A list containing data about individual hatching events.
 * @property commentsOrRemarks Additional comments or remarks regarding the hatching process.
 * @property invalidCommentsOrRemarksMessage A message indicating if the comments or remarks are invalid.
 * @property success Indicates whether the hatching entry can be saved.
 */
data class NestHatchingState(
    val nestCode: String = "",
    val invalidNestCodeMessage: String = "",
    val firstDayHatching: String = "",
    val invalidFirstDayHatchingMessage: String = "",
    val lastDayHatching: String = "",
    val invalidLastDayHatchingMessage: String = "",
    val hatchingDataList: MutableList<HatchingData> = mutableListOf(),
    val commentsOrRemarks: String = "",
    val invalidCommentsOrRemarksMessage: String = "",
    val success: Boolean = false

) {

    /**
     * Transforms the current state into a NestHatchingModel.
     *
     * @return A NestHatchingModel object containing the transformed data.
     */
    fun transformData(): NestHatchingModel {
        return NestHatchingModel(
            nestCode = nestCode,
            firstDayHatching = firstDayHatching,
            lastDayHatching = lastDayHatching,
            commentsOrRemarks = commentsOrRemarks,
            hatchingDataList = hatchingDataList.map { item ->
                item.transformData()
            }
        )
    }
}

/**
 * Data class representing individual hatching event data.
 *
 * @property selectedHatchingEvent The type of hatching event that was selected.
 * @property invalidHatchEventMessage A message indicating if the hatching event is invalid.
 */
data class HatchingData(
    val selectedHatchingEvent: String = "",
    val invalidHatchEventMessage: String = "",

    ) {

    /**
     * Transforms the current state into a NestHatchingDataModel.
     *
     * @return A NestHatchingDataModel object containing the transformed data.
     */
    fun transformData(): HatchingDataModel {
        return HatchingDataModel(
            selectedHatchingEvent = when (selectedHatchingEvent) {
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