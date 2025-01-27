package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a new nest.
 *
 * @property layingDate The date when the nest was laid.
 * @property area The area where the nest is located.
 * @property beach The beach where the nest is found.
 * @property sector The sector where the nest was found.
 * @property subsector The subsector where the nest was found.
 * @property nestCode The unique code assigned to the nest.
 * @property trackType The type of turtle track associated with the nest.
 * @property gpsLatitude The GPS latitude coordinate of the nest location.
 * @property gpsLongtitude The GPS longtitude coordinate of the nest location.
 * @property photoUri An optional URI pointing to a photo of the nest.
 * @property protectedNestSwitch A boolean indicating whether the nest is under protection.
 * @property protectionMeasures A description of the measures taken to protect the nest.
 * @property turtleTags Tags or identifiers associated with the turtle that laid the eggs.
 * @property emergenceOrEvent A description of any emergence events or related occurrences.
 * @property depthTopEgg The depth of the top egg in the nest.
 * @property distanceToSea The distance from the nest to the sea.
 * @property commentsOrRemarks Any additional comments or remarks regarding the new nest.
 */
data class NewNestModel(
    val layingDate: String,
    val area: String,
    val beach: String,
    val sector: String,
    val subsector: String,
    val nestCode: String,
    val trackType: String,
    val gpsLatitude: Double,
    val gpsLongtitude: Double,
    val photoUri: String?,
    val protectedNestSwitch: Boolean,
    val protectionMeasures: String,
    val turtleTags: String,
    val emergenceOrEvent: String,
    val depthTopEgg: Double,
    val distanceToSea: Double,
    val commentsOrRemarks: String
)