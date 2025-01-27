package com.spapalamprou.turtlesafe.features.newNest

import android.net.Uri

/**
 * Sealed class representing the various events that can occur in the new nest screen.
 */
sealed class NewNestEvent {

    /**
     * Event representing the selection of the laying date.
     *
     * @property layingDate The date when the eggs were laid.
     */
    data class LayingDateSelected(
        val layingDate: String
    ) : NewNestEvent()

    /**
     * Event representing the selection of the area.
     *
     * @property area The area selected by the user.
     */
    data class AreaSelected(
        val area: String
    ) : NewNestEvent()

    /**
     * Event triggered when the beach name is changed.
     *
     * @property beach The new beach name.
     */
    data class BeachChanged(
        val beach: String
    ) : NewNestEvent()

    /**
     * Event representing the selection of the sector.
     *
     * @property sector The sector selected by the user.
     */
    data class SectorSelected(
        val sector: String
    ) : NewNestEvent()

    /**
     * Event representing the selection of the subsector.
     *
     * @property subsector The subsector selected by the user.
     */
    data class SubsectorSelected(
        val subsector: String
    ) : NewNestEvent()

    /**
     * Event triggered when the nest code is changed.
     *
     * @property nestCode The new nest code.
     */
    data class NestCodeChanged(
        val nestCode: String
    ) : NewNestEvent()

    /**
     * Event representing the selection of the turtle's track type.
     *
     * @property trackType The selected track type.
     */
    data class TrackTypeSelected(
        val trackType: String
    ) : NewNestEvent()

    /**
     * Event triggered when the GPS location of the nest is received.
     *
     * @property gpsLatitude The latitude of the nest location.
     * @property gpsLongtitude The longtitude of the nest location.
     */
    data class NestLocationReceived(
        val gpsLatitude: String,
        val gpsLongtitude: String
    ) : NewNestEvent()

    /**
     * Event triggered when the GPS latitude is changed.
     *
     * @property gpsLatitude The new GPS latitude.
     */
    data class GpsLatitudeChanged(
        val gpsLatitude: String
    ) : NewNestEvent()

    /**
     * Event triggered when the GPS longitude is changed.
     *
     * @property gpsLongtitude The new GPS longtitude.
     */
    data class GpsLongtitudeChanged(
        val gpsLongtitude: String
    ) : NewNestEvent()

    /**
     * Event triggered when a photo is added.
     *
     * @property photoUri The URI of the new photo.
     */
    data class PhotoChanged(
        val photoUri: Uri?
    ) : NewNestEvent()

    /**
     * Event triggered when the protected nest switch is checked.
     *
     * @property protectedNestSwitch Indicates if the protected nest switch is checked.
     */
    data class ProtectedNestSwitchChecked(
        val protectedNestSwitch: Boolean
    ) : NewNestEvent()

    /**
     * Event representing the selection of protection measures.
     *
     * @property protectionMeasures The selected protection measures.
     */
    data class ProtectionMeasuresSelected(
        val protectionMeasures: String
    ) : NewNestEvent()

    /**
     * Event triggered when turtle tags are entered.
     *
     * @property turtleTags The turtle tags entered.
     */
    data class TurtleTagsChanged(
        val turtleTags: String
    ) : NewNestEvent()

    /**
     * Event triggered when the depth of the top egg is changed.
     *
     * @property depthTopEgg The depth of the top egg entered.
     */
    data class DepthTopEggChanged(
        val depthTopEgg: String
    ) : NewNestEvent()

    /**
     * Event triggered when the distance to the sea is changed.
     *
     * @property distanceToSea The new distance to the sea.
     */
    data class DistanceToSeaChanged(
        val distanceToSea: String
    ) : NewNestEvent()

    /**
     * Event triggered when comments or remarks are changed.
     *
     * @property commentsOrRemarks The new comments or remarks.
     */
    data class CommentsOrRemarksChanged(
        val commentsOrRemarks: String
    ) : NewNestEvent()

    /**
     * Event representing the selection of an emergence or event.
     *
     * @property emergenceOrEvent The selected emergence or event.
     */
    data class EmergenceOrEventSelected(
        val emergenceOrEvent: String
    ) : NewNestEvent()

    /**
     * Event triggered when the save button is clicked.
     */
    object SaveButtonClicked : NewNestEvent()
}