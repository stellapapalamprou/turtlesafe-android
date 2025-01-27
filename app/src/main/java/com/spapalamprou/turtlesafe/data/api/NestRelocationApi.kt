package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel
import com.google.gson.annotations.SerializedName

/**
 * Data class representing the relocation details of a nest.
 *
 * @property oldNestCode The original code of the nest before relocation.
 * @property relocatedTo The new location where the nest has been relocated.
 * @property sector The sector in which the new nest is located.
 * @property subsector The subsector in which the new nest is located.
 * @property reasonForRelocation The reason for relocating the nest.
 * @property newNestCode The code assigned to the nest after relocation.
 * @property depthTopEgg The depth (in meters) from the surface to the top egg.
 * @property depthBottomNest The depth (in meters) from the surface to the bottom of the nest.
 * @property distanceToSea The distance (in meters) from the new nest to the sea.
 * @property numOfEggsTransplanted The number of eggs that were transplanted to the new nest.
 * @property commentsOrRemarks Any comments or remarks about the relocation.
 */
data class NestRelocationJson(
    @SerializedName("old_nest_code") val oldNestCode: String,
    @SerializedName("relocated_to") val relocatedTo: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("subsector") val subsector: String,
    @SerializedName("reason_for_relocation") val reasonForRelocation: String,
    @SerializedName("new_nest_code") val newNestCode: String,
    @SerializedName("depth_top_egg") val depthTopEgg: Double,
    @SerializedName("depth_bottom_nest") val depthBottomNest: Double,
    @SerializedName("distance_to_sea") val distanceToSea: Double,
    @SerializedName("number_of_eggs_transplanted") val numOfEggsTransplanted: Int,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Extension function to serialize a NestRelocationModel to a NestRelocationJson format.
 *
 * @return The NestRelocationJson representation of the NestRelocationModel.
 */
fun NestRelocationModel.asJson(): NestRelocationJson {
    return NestRelocationJson(
        oldNestCode = oldNestCode,
        relocatedTo = relocatedTo,
        sector = sector,
        subsector = subsector,
        reasonForRelocation = reasonForRelocation,
        newNestCode = newNestCode,
        depthTopEgg = depthTopEgg,
        depthBottomNest = depthBottomNest,
        distanceToSea = distanceToSea,
        numOfEggsTransplanted = numOfEggsTransplanted,
        commentsOrRemarks = commentsOrRemarks
    )
}
