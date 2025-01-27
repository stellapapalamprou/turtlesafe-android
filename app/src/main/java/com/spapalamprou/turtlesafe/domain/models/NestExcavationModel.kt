package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a nest excavation.
 *
 * @property nestCode A code representing the specific nest being excavated.
 * @property hatchedEggs The number of eggs that successfully hatched.
 * @property pippedDeadHatchlings The number of hatchlings that pipped but did not survive.
 * @property pippedLiveHatchlings The number of hatchlings that pipped and survived.
 * @property noEmbryos The number of eggs that did not have embryos.
 * @property deadEmbryos The number of dead embryos found.
 * @property liveEmbryos The number of live embryos found.
 * @property deadHatchlingsNest The number of dead hatchlings.
 * @property liveHatchlingsNest The number of live hatchlings.
 * @property commentsOrRemarks Any additional comments or remarks regarding the excavation.
 */
data class NestExcavationModel(
    val nestCode: String,
    val hatchedEggs: Int,
    val pippedDeadHatchlings: Int,
    val pippedLiveHatchlings: Int,
    val noEmbryos: Int,
    val deadEmbryos: Int,
    val liveEmbryos: Int,
    val deadHatchlingsNest: Int,
    val liveHatchlingsNest: Int,
    val commentsOrRemarks: String
)