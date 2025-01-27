package com.spapalamprou.turtlesafe.features.newNest

import android.net.Uri
import com.spapalamprou.turtlesafe.domain.models.NewNestModel

/**
 * Data class representing the state of the new nest screen.
 *
 * @property layingDate The date when the eggs were laid.
 * @property invalidLayingDateMessage Message indicating if the laying date is invalid.
 * @property area The area where the nest is located.
 * @property invalidAreaMessage Message indicating if the area is invalid.
 * @property beach The name of the beach where the nest was found.
 * @property invalidBeachMessage Message indicating if the beach is invalid.
 * @property sector TThe sector where the nest was found.
 * @property invalidSectorMessage Message indicating if the sector is invalid.
 * @property subsector TThe subsector where the nest was found.
 * @property invalidSubsectorMessage Message indicating if the subsector is invalid.
 * @property nestCode A unique code assigned to the nest.
 * @property invalidNestCodeMessage Message indicating if the nest code is invalid.
 * @property trackType The type of turtle track associated with the nest.
 * @property invalidTrackTypeMessage Message indicating if the track type is invalid.
 * @property gpsLatitude The latitude of the nest's GPS coordinates.
 * @property invalidGpsLatitudeMessage Message indicating if the latitude is invalid.
 * @property gpsLongtitude The longtitude of the nest's GPS coordinates.
 * @property invalidGpsLongtitudeMessage Message indicating if the longitude is invalid.
 * @property photoUri The URI pointing to a photo of the nest.
 * @property protectedNestSwitch A boolean indicating whether the nest is under protection.
 * @property protectionMeasures The measures taken to protect the nest.
 * @property invalidProtectionMeasuresMessage Message indicating if the protection measures are invalid.
 * @property turtleTags Tags found on the turtle that laid the eggs.
 * @property invalidTurtleTagsMessage Message indicating if the turtle tags are invalid.
 * @property emergenceOrEvent Describes any emergence or event related to the nest.
 * @property invalidEmergenceOrEventMessage Message indicating if the emergence or event is invalid.
 * @property depthTopEgg The depth of the top egg in the nest.
 * @property invalidDepthTopEggMessage Message indicating if the depth of the top egg is invalid.
 * @property distanceToSea The distance from the nest to the sea.
 * @property invalidDistanceToSeaMessage Message indicating if the distance to the sea is invalid.
 * @property commentsOrRemarks Any additional comments or remarks about the nest.
 * @property invalidCommentsOrRemarksMessage Message indicating if the comments or remarks are invalid.
 * @property success Indicates whether the new nest entry can be saved.
 */
data class NewNestState(
    val layingDate: String = "",
    val invalidLayingDateMessage: String = "",
    val area: String = "",
    val invalidAreaMessage: String = "",
    val beach: String = "",
    val invalidBeachMessage: String = "",
    val sector: String = "",
    val invalidSectorMessage: String = "",
    val subsector: String = "",
    val invalidSubsectorMessage: String = "",
    val nestCode: String = "",
    val invalidNestCodeMessage: String = "",
    val trackType: String = "",
    val invalidTrackTypeMessage: String = "",
    val gpsLatitude: String = "",
    val invalidGpsLatitudeMessage: String = "",
    val gpsLongtitude: String = "",
    val invalidGpsLongtitudeMessage: String = "",
    val photoUri: Uri? = null,
    val protectedNestSwitch: Boolean = false,
    val protectionMeasures: String = "",
    val invalidProtectionMeasuresMessage: String = "",
    val turtleTags: String = "",
    val invalidTurtleTagsMessage: String = "",
    val emergenceOrEvent: String = "",
    val invalidEmergenceOrEventMessage: String = "",
    val depthTopEgg: String = "",
    val invalidDepthTopEggMessage: String = "",
    val distanceToSea: String = "",
    val invalidDistanceToSeaMessage: String = "",
    val commentsOrRemarks: String = "",
    val invalidCommentsOrRemarksMessage: String = "",
    val success: Boolean = false

) {

    /**
     * Transforms the current state into a NewNestModel.
     *
     * @return A NewNestModel object containing the transformed data.
     */
    fun transformData(): NewNestModel {
        return NewNestModel(
            layingDate = layingDate,
            area = area,
            beach = beach,
            sector = sector,
            subsector = subsector,
            nestCode = nestCode,
            trackType = trackType,
            gpsLatitude = gpsLatitude.toDouble(),
            gpsLongtitude = gpsLongtitude.toDouble(),
            photoUri = photoUri?.toString(),
            protectedNestSwitch = protectedNestSwitch,
            protectionMeasures = if (protectedNestSwitch) {
                when (protectionMeasures) {
                    "A(d): Nest shading (Alleyway)" -> "A(d)"
                    "A(h): Nest fencing (Alleyway)" -> "A(h)"
                    "B: Boxed nest" -> "B"
                    "C(b): Cage (bamboo)" -> "C(b)"
                    "C(i): Cage (iron)" -> "C(i)"
                    "G(d): Guard (day)" -> "G(d)"
                    "G(n#): Guard (night)" -> "G(n#)"
                    "H: Hatchery nest" -> "H"
                    "R: Relocated nest" -> "R"
                    "T: Red/White tape" -> "T"
                    "O: Other" -> "O"
                    else -> ""
                }
            } else {
                ""
            },
            turtleTags = turtleTags,
            emergenceOrEvent = emergenceOrEvent,
            depthTopEgg = depthTopEgg.toDouble(),
            distanceToSea = distanceToSea.toDouble(),
            commentsOrRemarks = commentsOrRemarks
        )
    }
}