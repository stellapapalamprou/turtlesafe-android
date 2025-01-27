package com.spapalamprou.turtlesafe.features.nestExcavation

/**
 * Sealed class representing the various events that can occur in the nest excavation screen.
 */
sealed class NestExcavationEvent {

    /**
     * Event triggered when the nest code is changed.
     *
     * @property nestCode The nest code entered by the user.
     */
    data class NestCodeChanged(
        val nestCode: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of hatched eggs is changed.
     *
     * @property hatchedEggs The updated number of hatched eggs.
     */
    data class HatchedEggsChanged(
        val hatchedEggs: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of pipped dead hatchlings is changed.
     *
     * @property pippedDeadHatchlings The updated number of pipped dead hatchlings.
     */
    data class PippedDeadHatchlingsChanged(
        val pippedDeadHatchlings: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of pipped live hatchlings is changed.
     *
     * @property pippedLiveHatchlings The updated number of pipped live hatchlings.
     */
    data class PippedLiveHatchlingsChanged(
        val pippedLiveHatchlings: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of no embryos is changed.
     *
     * @property noEmbryos The updated number of no embryos.
     */
    data class NoEmbryosChanged(
        val noEmbryos: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of dead embryos is changed.
     *
     * @property deadEmbryos The updated number of dead embryos.
     */
    data class DeadEmbryosChanged(
        val deadEmbryos: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of live embryos is changed.
     *
     * @property liveEmbryos The updated number of live embryos.
     */
    data class LiveEmbryosChanged(
        val liveEmbryos: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of dead hatchlings is changed.
     *
     * @property deadHatchlingsNest The updated number of dead hatchlings.
     */
    data class DeadHatchlingsNestChanged(
        val deadHatchlingsNest: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the number of live hatchlings is changed.
     *
     * @property liveHatchlingsNest The updated number of live hatchlings.
     */
    data class LiveHatchlingsNestChanged(
        val liveHatchlingsNest: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when comments or remarks are changed.
     *
     * @property commentsOrRemarks The updated comments or remarks related to the nest excavation.
     */
    data class CommentsOrRemarksChanged(
        val commentsOrRemarks: String
    ) : NestExcavationEvent()

    /**
     * Event triggered when the save button is clicked.
     */
    object SaveButtonClicked : NestExcavationEvent()
}