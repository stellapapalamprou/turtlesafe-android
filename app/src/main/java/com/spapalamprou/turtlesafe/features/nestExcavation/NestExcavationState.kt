package com.spapalamprou.turtlesafe.features.nestExcavation

import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel

/**
 * Data class representing the state of the nest excavation screen.
 *
 * @property nestCode The code of the nest being excavated.
 * @property invalidNestCodeMessage A message indicating if the nest code input is invalid.
 * @property hatchedEggs The number of eggs that have hatched.
 * @property invalidHatchedEggsMessage A message indicating if the hatched eggs input is invalid.
 * @property pippedDeadHatchlings The number of pipped dead hatchlings.
 * @property invalidPippedDeadHatchlingsMessage A message indicating if the pipped dead hatchlings input is invalid.
 * @property pippedLiveHatchlings The number of pipped live hatchlings.
 * @property invalidPippedLiveHatchlingsMessage A message indicating if the pipped live hatchlings input is invalid.
 * @property noEmbryos The number of eggs that did not have embryos.
 * @property invalidNoEmbryosMessage A message indicating if the no embryos input is invalid.
 * @property deadEmbryos The number of dead embryos.
 * @property invalidDeadEmbryosMessage A message indicating if the dead embryos input is invalid.
 * @property liveEmbryos The number of live embryos.
 * @property invalidLiveEmbryosMessage A message indicating if the live embryos input is invalid.
 * @property deadHatchlingsNest The number of dead hatchlings found.
 * @property invalidDeadHatchlingsNestMessage A message indicating if the dead hatchlings input is invalid.
 * @property liveHatchlingsNest The number of live hatchlings found.
 * @property invalidLiveHatchlingsNestMessage A message indicating if the live hatchlings input is invalid.
 * @property commentsOrRemarks Any additional comments or remarks related to the excavation.
 * @property invalidCommentsOrRemarksMessage A message indicating if the comments or remarks input is invalid.
 * @property success Indicates whether the excavation entry can be saved.
 */
data class NestExcavationState(
    val nestCode: String = "",
    val invalidNestCodeMessage: String = "",
    val hatchedEggs: String = "",
    val invalidHatchedEggsMessage: String = "",
    val pippedDeadHatchlings: String = "",
    val invalidPippedDeadHatchlingsMessage: String = "",
    val pippedLiveHatchlings: String = "",
    val invalidPippedLiveHatchlingsMessage: String = "",
    val noEmbryos: String = "",
    val invalidNoEmbryosMessage: String = "",
    val deadEmbryos: String = "",
    val invalidDeadEmbryosMessage: String = "",
    val liveEmbryos: String = "",
    val invalidLiveEmbryosMessage: String = "",
    val deadHatchlingsNest: String = "",
    val invalidDeadHatchlingsNestMessage: String = "",
    val liveHatchlingsNest: String = "",
    val invalidLiveHatchlingsNestMessage: String = "",
    val commentsOrRemarks: String = "",
    val invalidCommentsOrRemarksMessage: String = "",
    val success: Boolean = false

) {

    /**
     * Transforms the current state into a NestExcavationModel.
     *
     * @return A NestExcavationModel object containing the transformed data.
     */
    fun transformData(): NestExcavationModel {
        return NestExcavationModel(
            nestCode = nestCode,
            hatchedEggs = hatchedEggs.toInt(),
            pippedDeadHatchlings = pippedDeadHatchlings.toInt(),
            pippedLiveHatchlings = pippedLiveHatchlings.toInt(),
            noEmbryos = noEmbryos.toInt(),
            deadEmbryos = deadEmbryos.toInt(),
            liveEmbryos = liveEmbryos.toInt(),
            deadHatchlingsNest = deadHatchlingsNest.toInt(),
            liveHatchlingsNest = liveHatchlingsNest.toInt(),
            commentsOrRemarks = commentsOrRemarks
        )
    }
}