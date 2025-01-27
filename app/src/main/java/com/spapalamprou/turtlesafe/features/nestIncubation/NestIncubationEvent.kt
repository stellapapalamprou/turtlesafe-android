package com.spapalamprou.turtlesafe.features.nestIncubation

/**
 * Sealed class representing the various events that can occur in the nest incubation screen.
 */
sealed class NestIncubationEvent {

    /**
     * Event triggered when the nest code is changed.
     *
     * @param nestCode The updated nest code entered by the user.
     */
    data class NestCodeChanged(
        val nestCode: String
    ) : NestIncubationEvent()

    /**
     * Event triggered when a nest location is selected.
     *
     * @param nestLocation The selected nest location.
     */
    data class NestLocationSelected(
        val nestLocation: String
    ) : NestIncubationEvent()

    /**
     * Event triggered when the hatchery code is changed.
     *
     * @param hatcheryCode The updated hatchery code entered by the user.
     */
    data class HatcheryCodeChanged(
        val hatcheryCode: String
    ) : NestIncubationEvent()

    /**
     * Event triggered when the add button for a new incubation event is clicked.
     */
    object IncEventAddButtonClicked : NestIncubationEvent()

    /**
     * Event triggered when a delete button for a specific incubation event is clicked.
     *
     * @param index The index of the incubation event to be deleted from the list.
     */
    data class DeleteIncEventButtonClicked(
        val index: Int
    ) : NestIncubationEvent()

    /**
     * Event triggered when the type of an incubation event is selected.
     *
     * @param index The index of the event in the list.
     * @param eventType The selected incubation event.
     */
    data class IncEventTypeSelected(
        val index: Int,
        val eventType: String
    ) : NestIncubationEvent()

    /**
     * Event triggered when the protected nest switch is checked or unchecked.
     *
     * @param protectedNestSwitch A boolean indicating the new state of the switch.
     */
    data class ProtectedNestSwitchChecked(
        val protectedNestSwitch: Boolean
    ) : NestIncubationEvent()

    /**
     * Event triggered when protection measures for the nest are selected.
     *
     * @param protectionMeasures The selected protection measures.
     */
    data class ProtectionMeasuresSelected(
        val protectionMeasures: String
    ) : NestIncubationEvent()

    /**
     * Event triggered when the comments or remarks field is changed.
     *
     * @param commentsOrRemarks The updated comments or remarks entered by the user.
     */
    data class CommentsOrRemarksChanged(
        val commentsOrRemarks: String
    ) : NestIncubationEvent()

    /**
     * Event triggered when the save button is clicked to save incubation data.
     */
    object SaveButtonClicked : NestIncubationEvent()
}