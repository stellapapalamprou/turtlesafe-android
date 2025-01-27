package com.spapalamprou.turtlesafe.features.nestRelocation

/**
 * Sealed class representing the various events that can occur in the nest relocation screen.
 */
sealed class NestRelocationEvent {

    /**
     * Event triggered when the old nest code is changed.
     *
     * @property oldNestCode The new old nest code entered by the user.
     */
    data class OldNestCodeChanged(
        val oldNestCode: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when a relocation destination is selected.
     *
     * @property relocatedTo The location to which the nest is being relocated.
     */
    data class RelocatedToSelected(
        val relocatedTo: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when a sector is selected.
     *
     * @property sector The sector selected by the user.
     */
    data class SectorSelected(
        val sector: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when a subsector is selected.
     *
     * @property subsector The subsector selected by the user.
     */
    data class SubsectorSelected(
        val subsector: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when a reason for relocation is entered.
     *
     * @property reasonForRelocation The reason for relocating the nest.
     */
    data class ReasonForRelocationSelected(
        val reasonForRelocation: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when the new nest code is changed.
     *
     * @property newNestCode The new nest code entered by the user.
     */
    data class NewNestCodeChanged(
        val newNestCode: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when the depth of the top egg is changed.
     *
     * @property depthTopEgg The new depth value for the top egg.
     */
    data class DepthTopEggChanged(
        val depthTopEgg: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when the depth of the bottom nest is changed.
     *
     * @property depthToBottomNest The new depth value for the bottom nest.
     */
    data class DepthToBottomNestChanged(
        val depthToBottomNest: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when the distance to the sea is changed.
     *
     * @property distanceToSea The new distance value to the sea.
     */
    data class DistanceToSeaChanged(
        val distanceToSea: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when the number of eggs transplanted is changed.
     *
     * @property numOfEggsTransplanted The new number of eggs that have been transplanted.
     */
    data class NumOfEggsTransplantedChanged(
        val numOfEggsTransplanted: String = ""
    ) : NestRelocationEvent()


    /**
     * Event triggered when comments or remarks are changed.
     *
     * @property commentsOrRemarks The new comments or remarks related to the relocation.
     */
    data class CommentsOrRemarksChanged(
        val commentsOrRemarks: String = ""
    ) : NestRelocationEvent()

    /**
     * Event triggered when the save button is clicked.
     */
    object SaveButtonClicked : NestRelocationEvent()
}