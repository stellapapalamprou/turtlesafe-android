package com.spapalamprou.turtlesafe.features.nestRelocation

import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel

/**
 * Data class representing the state of a nest relocation screen.
 *
 * @property oldNestCode The code of the old nest.
 * @property invalidOldNestCodeMessage Message indicating if the old nest code is invalid.
 * @property relocatedTo The location to which the nest has been relocated.
 * @property invalidRelocatedToMessage Message indicating if the relocated location is invalid.
 * @property sector The sector in which the nest is relocated.
 * @property invalidSectorMessage Message indicating if the sector input is invalid.
 * @property subsector The subsector in which the nest is relocated.
 * @property invalidSubsectorMessage Message indicating if the subsector input is invalid.
 * @property reasonForRelocation The reason for relocating the nest.
 * @property invalidReasonForRelocationMessage Message indicating if the reason for relocation is invalid.
 * @property newNestCode The code for the new nest.
 * @property invalidNewNestCodeMessage Message indicating if the new nest code is invalid.
 * @property depthTopEgg The depth at which the top egg is located.
 * @property invalidDepthTopEggMessage Message indicating if the depth of the top egg is invalid.
 * @property depthBottomNest The depth of the nest.
 * @property invalidDepthBottomNestMessage Message indicating if the depth of the nest is invalid.
 * @property distanceToSea The distance from the nest to the sea.
 * @property invalidDistanceToSeaMessage Message indicating if the distance to the sea is invalid.
 * @property numOfEggsTransplanted The number of eggs that have been transplanted.
 * @property invalidNumOfEggsTransplanted Message indicating if the number of eggs transplanted is invalid.
 * @property commentsOrRemarks Any additional comments or remarks about the relocation.
 * @property invalidCommentsOrRemarksMessage Message indicating if the comments or remarks are invalid.
 * @property success Indicates whether the relocation entry can be saved.
 */
data class NestRelocationState(
    val oldNestCode: String = "",
    val invalidOldNestCodeMessage: String = "",
    val relocatedTo: String = "",
    val invalidRelocatedToMessage: String = "",
    val sector: String = "",
    val invalidSectorMessage: String = "",
    val subsector: String = "",
    val invalidSubsectorMessage: String = "",
    val reasonForRelocation: String = "",
    val invalidReasonForRelocationMessage: String = "",
    val newNestCode: String = "",
    val invalidNewNestCodeMessage: String = "",
    val depthTopEgg: String = "",
    val invalidDepthTopEggMessage: String = "",
    val depthBottomNest: String = "",
    val invalidDepthBottomNestMessage: String = "",
    val distanceToSea: String = "",
    val invalidDistanceToSeaMessage: String = "",
    val numOfEggsTransplanted: String = "",
    val invalidNumOfEggsTransplanted: String = "",
    val commentsOrRemarks: String = "",
    val invalidCommentsOrRemarksMessage: String = "",
    val success: Boolean = false
) {

    /**
     * Transforms the current state into a NestRelocationModel.
     *
     * @return A NestRelocationModel object containing the transformed data.
     */
    fun transformData(): NestRelocationModel {
        return NestRelocationModel(
            oldNestCode = oldNestCode,
            relocatedTo = when (relocatedTo) {
                "BoB: Back of Beach" -> "BoB"
                "H: Hatchery" -> "H"
                else -> ""
            },
            sector = sector,
            subsector = subsector,
            reasonForRelocation = when (reasonForRelocation) {
                "I: Inundation" -> "I"
                "T: Trampling" -> "T"
                "R: Root Invasion" -> "R"
                "O: Other" -> "O"
                else -> ""
            },
            newNestCode = newNestCode,
            depthTopEgg = depthTopEgg.toDouble(),
            depthBottomNest = depthBottomNest.toDouble(),
            distanceToSea = distanceToSea.toDouble(),
            numOfEggsTransplanted = numOfEggsTransplanted.toInt(),
            commentsOrRemarks = commentsOrRemarks
        )
    }
}