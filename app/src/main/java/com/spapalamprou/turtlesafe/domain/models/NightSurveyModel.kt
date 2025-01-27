package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a night survey.
 *
 * @property date The date when the night survey was conducted.
 * @property area The area where the night survey took place.
 * @property beach The specific beach where the survey was conducted.
 * @property sector The sector where the survey was conducted.
 * @property subsector The subsector where the survey was conducted.
 * @property tagger The name of the individual conducting the survey.
 * @property oldTagDataList The list of old tags found on the turtle.
 * @property newTagDataList The list of new tags placed on the turtle.
 * @property lostTagScars A description of any lost tag scars observed.
 * @property straightCarapaceLength The straight carapace length of the turtle.
 * @property straightCarapaceWidth The straight carapace width of the turtle.
 * @property curvedCarapaceLength The curved carapace length of the turtle.
 * @property curvedCarapaceWidth The curved carapace width of the turtle.
 * @property damageFlippersHead A description of any observed damage to the turtle's flippers or head.
 * @property damageCarapace A description of any observed damage to the turtle's carapace.
 * @property nestEmergenceSwitch A boolean indicating whether there was a nest emergence.
 * @property nestCode The code of the nest associated with this survey, if applicable.
 * @property depthTopEgg The depth of the top egg in the nest.
 * @property depthBottomNests The depth of the bottom of the nests.
 * @property distanceToSea The distance from the nest to the sea.
 * @property numEggsRelocated The number of eggs that were relocated during the survey.
 * @property relocationComments Any comments related to the relocation of eggs.
 * @property numEggsExcavated The number of eggs that were excavated during the survey.
 * @property startLaying The time when the laying process began.
 * @property startCover The time when the covering of the nest began.
 * @property startCamouflage The time when the camouflage of the nest began.
 * @property departNestSite The time of the turtle's departure from the nest site.
 * @property turtleAtSea The time of the turtle's return at sea after leaving the nest.
 * @property commentsRemarks Any additional comments or remarks regarding the night survey.
 */
data class NightSurveyModel(
    val date: String,
    val area: String,
    val beach: String,
    val sector: String,
    val subsector: String,
    val tagger: String,
    val oldTagDataList: List<OldTagDataModel>,
    val newTagDataList: List<NewTagDataModel>,
    val lostTagScars: String,
    val straightCarapaceLength: Double,
    val straightCarapaceWidth: Double,
    val curvedCarapaceLength: Double,
    val curvedCarapaceWidth: Double,
    val damageFlippersHead: String,
    val damageCarapace: String,
    val nestEmergenceSwitch: Boolean,
    val nestCode: String?,
    val depthTopEgg: Double?,
    val depthBottomNests: Double?,
    val distanceToSea: Double?,
    val numEggsRelocated: Int?,
    val relocationComments: String?,
    val numEggsExcavated: Int?,
    val startLaying: String?,
    val startCover: String?,
    val startCamouflage: String?,
    val departNestSite: String,
    val turtleAtSea: String,
    val commentsRemarks: String
)

/**
 * Model representing an old tag.
 *
 * @property selectedOldTagLocation The turtle's body part where the old tag was found.
 * @property oldTagCode The code of the old tag.
 * @property isTurtleSafeSwitchChecked A boolean indicating whether the tag is an TurtleSafe tag.
 * @property tagNotes Any additional notes related to the old tag.
 */
data class OldTagDataModel(
    val selectedOldTagLocation: String,
    val oldTagCode: String,
    val isTurtleSafeSwitchChecked: Boolean,
    val tagNotes: String?
)

/**
 * Model representing a new tag.
 *
 * @property selectedNewTagLocation The turtle's body part where the new tag is applied.
 * @property newTagCode The code of the new tag.
 * @property isTagSwitchChecked A boolean indicating whether the tagging was successful.
 * @property selectedNewScarType The type of scar associated with the new tag, if applicable.
 * @property newScarLocation The location of the new scar, if applicable.
 */
data class NewTagDataModel(
    val selectedNewTagLocation: String,
    val newTagCode: String,
    val isTagSwitchChecked: Boolean,
    val selectedNewScarType: String?,
    val newScarLocation: String?
)