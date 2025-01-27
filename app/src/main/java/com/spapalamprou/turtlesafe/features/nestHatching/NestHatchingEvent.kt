package com.spapalamprou.turtlesafe.features.nestHatching

/**
 * Sealed class representing the various events that can occur in the nest hatching screen.
 */
sealed class NestHatchingEvent {

    /**
     * Event triggered when the nest code changes.
     *
     * @property nestCode The new nest code that has been input by the user.
     */
    data class NestCodeChanged(
        val nestCode: String
    ) : NestHatchingEvent()

    /**
     * Event triggered when the first day of hatching is selected.
     *
     * @property date The date selected as the first day of hatching.
     */
    data class FirstDayOfHatchingSelected(
        val date: String
    ) : NestHatchingEvent()

    /**
     * Event triggered when the last day of hatching is selected.
     *
     * @property date The date selected as the last day of hatching.
     */
    data class LastDayOfHatchingSelected(
        val date: String
    ) : NestHatchingEvent()

    /**
     * Event triggered when the comments or remarks are changed.
     *
     * @property commentsOrRemarks The updated comments or remarks provided by the user.
     */
    data class CommentsOrRemarksChanged(
        val commentsOrRemarks: String
    ) : NestHatchingEvent()

    /**
     * Event triggered when the button to add a new hatching event is clicked.
     */
    object HatchEventAddButtonClicked : NestHatchingEvent()

    /**
     * Event triggered when the button to delete a hatching event is clicked.
     *
     * @property index The index of the hatching event to be deleted from the list.
     */
    data class DeleteHatchEventButtonClicked(
        val index: Int
    ) : NestHatchingEvent()

    /**
     * Event triggered when a specific hatching event type is selected.
     *
     * @property index The index of the hatching event in the list.
     * @property eventType The type of the hatching event selected by the user.
     */
    data class HatchEventTypeSelected(
        val index: Int,
        val eventType: String
    ) : NestHatchingEvent()

    /**
     * Event triggered when the save button is clicked to save hatching data.
     */
    object SaveButtonClicked : NestHatchingEvent()
}