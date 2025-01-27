package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a nest relocation.
 *
 * @property oldNestCode The code representing the nest from which the eggs were relocated.
 * @property relocatedTo The new location where the nest has been relocated.
 * @property sector The sector where the new nest is located.
 * @property subsector The subsector where the new nest is located.
 * @property reasonForRelocation A description of the reason for relocating the nest.
 * @property newNestCode The code assigned to the new nest after relocation.
 * @property depthTopEgg The depth of the top egg in the nest.
 * @property depthBottomNest The depth of the bottom of the nest.
 * @property distanceToSea The distance from the nest to the sea.
 * @property numOfEggsTransplanted The number of eggs that were transplanted to the new nest.
 * @property commentsOrRemarks Any additional comments or remarks regarding the relocation event.
 */
data class NestRelocationModel(
    val oldNestCode: String,
    val relocatedTo: String,
    val sector: String,
    val subsector: String,
    val reasonForRelocation: String,
    val newNestCode: String,
    val depthTopEgg: Double,
    val depthBottomNest: Double,
    val distanceToSea: Double,
    val numOfEggsTransplanted: Int,
    val commentsOrRemarks: String
)