package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel
import com.google.gson.annotations.SerializedName

/**
 * Data class representing the details of a nest excavation.
 *
 * @property nestCode The unique nest code.
 * @property hatchedEggs The number of eggs that successfully hatched.
 * @property pippedDeadHatchlings The number of pipped hatchlings found dead during excavation.
 * @property pippedLiveHatchlings The number of pipped hatchlings found alive during excavation.
 * @property noEmbryos The number of eggs with no embryos found.
 * @property deadEmbryos The number of embryos that were found dead.
 * @property liveEmbryos The number of embryos that were found alive.
 * @property deadHatchlingsNest The number of dead hatchlings found inside the nest.
 * @property liveHatchlingsNest The number of live hatchlings found inside the nest.
 * @property commentsOrRemarks Any comments or remarks about the nest excavation.
 */
data class NestExcavationJson(
    @SerializedName("nest_code") val nestCode: String,
    @SerializedName("hatched_eggs") val hatchedEggs: Int,
    @SerializedName("pipped_dead_hatchlings") val pippedDeadHatchlings: Int,
    @SerializedName("pipped_live_hatchlings") val pippedLiveHatchlings: Int,
    @SerializedName("no_embryos") val noEmbryos: Int,
    @SerializedName("dead_embryos") val deadEmbryos: Int,
    @SerializedName("live_embryos") val liveEmbryos: Int,
    @SerializedName("dead_hatchlings_nest") val deadHatchlingsNest: Int,
    @SerializedName("live_hatchlings_nest") val liveHatchlingsNest: Int,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Extension function to serialize a NestExcavationModel to a NestExcavationJson format.
 *
 * @return The NestExcavationJson representation of the NestExcavationModel.
 */
fun NestExcavationModel.asJson(): NestExcavationJson {
    return NestExcavationJson(
        nestCode = nestCode,
        hatchedEggs = hatchedEggs,
        pippedDeadHatchlings = pippedDeadHatchlings,
        pippedLiveHatchlings = pippedLiveHatchlings,
        noEmbryos = noEmbryos,
        deadEmbryos = deadEmbryos,
        liveEmbryos = liveEmbryos,
        deadHatchlingsNest = deadHatchlingsNest,
        liveHatchlingsNest = liveHatchlingsNest,
        commentsOrRemarks = commentsOrRemarks
    )
}
