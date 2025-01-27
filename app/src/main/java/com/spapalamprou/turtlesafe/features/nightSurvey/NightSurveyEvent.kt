package com.spapalamprou.turtlesafe.features.nightSurvey

/**
 * Sealed class representing the various events that can occur in the night survey screen.
 */
sealed class NightSurveyEvent {

    /**
     * Event triggered when the date of the night survey is selected.
     *
     * @param date The selected survey date in a String format.
     */
    data class DateSelected(
        val date: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when an area is selected.
     *
     * @param area The selected survey area.
     */
    data class AreaSelected(
        val area: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the beach name is changed.
     *
     * @param beach The updated beach name.
     */
    data class BeachChanged(
        val beach: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when a sector is selected.
     *
     * @param sector The selected sector.
     */
    data class SectorSelected(
        val sector: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when a subsector is selected.
     *
     * @param subsector The selected subsector.
     */
    data class SubsectorSelected(
        val subsector: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the tagger's name is entered.
     *
     * @param tagger The tagger's name.
     */
    data class TaggerChanged(
        val tagger: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the user clicks the button to add an old tag.
     */
    object AddOldTagButtonClicked : NightSurveyEvent()

    /**
     * Event triggered when the user clicks the button to delete an old tag.
     *
     * @param index The index of the tag to be deleted from the list.
     */
    data class DeleteOldTagButtonClicked(
        val index: Int
    ) : NightSurveyEvent()

    /**
     * Event triggered when a tag location is selected for an old tag.
     *
     * @param index The index of the tag in the list.
     * @param tagLocation The selected location of the tag.
     */
    data class OldTagLocationSelected(
        val index: Int,
        val tagLocation: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the code of an old tag is changed.
     *
     * @param index The index of the tag in the list.
     * @param tagCode The updated tag code.
     */
    data class OldTagCodeChanged(
        val index: Int,
        val tagCode: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the TurtleSafe tag switch is checked for an old tag.
     *
     * @param index The index of the tag in the list.
     * @param switch The switch state indicating whether it's an TurtleSafe tag.
     */
    data class TurtleSafeTagSwitchChecked(
        val index: Int,
        val switch: Boolean
    ) : NightSurveyEvent()

    /**
     * Event triggered when notes are entered for an old tag.
     *
     * @param index The index of the tag in the list.
     * @param tagNotes The updated notes for the tag.
     */
    data class TagNotesChanged(
        val index: Int,
        val tagNotes: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the user clicks the button to add a new tag.
     */
    object AddNewTagButtonClicked : NightSurveyEvent()

    /**
     * Event triggered when the user clicks the button to delete a new tag.
     *
     * @param index The index of the new tag to be deleted from the list.
     */
    data class DeleteNewTagButtonClicked(
        val index: Int
    ) : NightSurveyEvent()

    /**
     * Event triggered when a tag location is selected for a new tag.
     *
     * @param index The index of the tag in the list.
     * @param tagLocation The selected location of the new tag.
     */
    data class NewTagLocationSelected(
        val index: Int,
        val tagLocation: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the code of a new tag is changed.
     *
     * @param index The index of the new tag in the list.
     * @param tagCode The updated tag code.
     */
    data class NewTagCodeChanged(
        val index: Int,
        val tagCode: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the switch indicating that tagging was successful is checked for a new tag.
     *
     * @param index The index of the new tag in the list.
     * @param switch The switch indicating whether tagging was successful.
     */
    data class TaggingSuccessfulSwitchChecked(
        val index: Int,
        val switch: Boolean
    ) : NightSurveyEvent()

    /**
     * Event triggered when a scar type is selected for a new scar.
     *
     * @param index The index of the scar in the list.
     * @param scarType The selected type of scar.
     */
    data class NewScarTypeSelected(
        val index: Int,
        val scarType: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the scar location is changed for a new scar.
     *
     * @param index The index of the scar in the list.
     * @param scarLocation The updated location of the scar.
     */
    data class NewScarLocationChanged(
        val index: Int,
        val scarLocation: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the lost tag scars are updated.
     *
     * @param lostTagScars The updated lost tag scars description.
     */
    data class LostTagScarsChanged(
        val lostTagScars: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the straight carapace length is updated.
     *
     * @param strCarapaceLength The straight carapace length value entered.
     */
    data class StraightCarapaceLengthChanged(
        val strCarapaceLength: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the straight carapace width is updated.
     *
     * @param strCarapaceWidth The straight carapace width value entered.
     */
    data class StraightCarapaceWidthChanged(
        val strCarapaceWidth: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the curved carapace length is updated.
     *
     * @param crvCarapaceLength The curved carapace length value entered.
     */
    data class CurvedCarapaceLengthChanged(
        val crvCarapaceLength: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the curved carapace width is updated.
     *
     * @param crvCarapaceWidth The curved carapace width value entered.
     */
    data class CurvedCarapaceWidthChanged(
        val crvCarapaceWidth: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the damage of flippers or head is entered.
     *
     * @param damageFlippersHead The description of the damage to flippers or head.
     */
    data class DamageFlippersHeadChanged(
        val damageFlippersHead: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the damage of the carapace is entered.
     *
     * @param damageCarapace The description of the damage to the carapace.
     */
    data class DamageCarapaceChanged(
        val damageCarapace: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the nesting emergence switch is checked.
     *
     * @param switch The switch indicating a nesting emergence.
     */
    data class NestingEmergenceSwitchChecked(
        val switch: Boolean
    ) : NightSurveyEvent()

    /**
     * Event triggered when the nest code is changed.
     *
     * @param nestCode The nest code entered.
     */
    data class NestCodeChanged(
        val nestCode: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the depth to the top egg is changed.
     *
     * @param depthTopEgg The new depth to the top egg.
     */
    data class DepthTopEggChanged(
        val depthTopEgg: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the depth to the bottom of the nest is changed.
     *
     * @param depthToBottomNest The new depth to the bottom of the nest.
     */
    data class DepthToBottomNestsChanged(
        val depthToBottomNest: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the distance to the sea is changed.
     *
     * @param distanceToSea The new distance to the sea.
     */
    data class DistanceToSeaChanged(
        val distanceToSea: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the number of relocated eggs is changed.
     *
     * @param numOfEggsRelocated The new number of relocated eggs.
     */
    data class NumOfEggsRelocatedChanged(
        val numOfEggsRelocated: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when relocation comments are changed.
     *
     * @param relocationComments The new relocation comments.
     */
    data class RelocationCommentsChanged(
        val relocationComments: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the number of excavated eggs is changed.
     *
     * @param numOfEggsExcavated The new number of excavated eggs.
     */
    data class NumOfEggsExcavatedChanged(
        val numOfEggsExcavated: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the start of laying is selected.
     *
     * @param startLaying The time indicating when the turtle started laying eggs.
     */
    data class StartLayingSelected(
        val startLaying: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the start of covering is selected.
     *
     * @param startCover The time indicating when the turtle started covering the eggs.
     */
    data class StartCoverSelected(
        val startCover: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the start of camouflage is selected.
     *
     * @param startCamouflage The time indicating when the turtle started camouflaging the eggs.
     */
    data class StartCamouflageSelected(
        val startCamouflage: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the turtle's departure from the nest site is selected.
     *
     * @param departNestSite The time indicating when the turtle started camouflaging the eggs.
     */
    data class DepartNestSiteSelected(
        val departNestSite: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the turtle's return at sea is selected.
     *
     * @param turtleAtSea The time indicating when the turtle returned at sea.
     */
    data class TurtleAtSeaSelected(
        val turtleAtSea: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when comments or remarks are changed.
     *
     * @param commentsRemarks The new comments or remarks.
     */
    data class CommentsRemarksChanged(
        val commentsRemarks: String
    ) : NightSurveyEvent()

    /**
     * Event triggered when the submit button is clicked.
     */
    object SubmitButtonClicked : NightSurveyEvent()
}