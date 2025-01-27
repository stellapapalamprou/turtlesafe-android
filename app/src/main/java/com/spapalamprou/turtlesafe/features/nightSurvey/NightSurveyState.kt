package com.spapalamprou.turtlesafe.features.nightSurvey

import com.spapalamprou.turtlesafe.domain.models.NewTagDataModel
import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.models.OldTagDataModel

/**
 * Data class representing the state of the night survey screen.
 *
 * @param date The date when the survey is conducted.
 * @param invalidDateMessage A message indicating an invalid date input.
 * @param area The area where the survey is conducted.
 * @param invalidAreaMessage A message indicating an invalid area input.
 * @param beach The beach where the survey is conducted.
 * @param invalidBeachMessage A message indicating an invalid beach input.
 * @param sector The sector where the survey is conducted.
 * @param invalidSectorMessage A message indicating an invalid sector input.
 * @param subsector The subsector where the survey is conducted.
 * @param invalidSubsectorMessage A message indicating an invalid subsector input.
 * @param tagger The name of the tagger conducting the survey.
 * @param invalidTaggerMessage A message indicating an invalid tagger name.
 * @param oldTagDataList A list of old tag data.
 * @param newTagDataList A list of new tag data.
 * @param lostTagScars Information about scars caused by lost tags.
 * @param invalidLostTagScarsMessage A message indicating invalid input for lost tags scars.
 * @param straightCarapaceLength The straight carapace length.
 * @param invalidStraightCarapaceLengthMessage A message indicating an invalid straight carapace length.
 * @param straightCarapaceWidth The straight carapace width.
 * @param invalidStraightCarapaceWidthMessage A message indicating an invalid straight carapace width.
 * @param curvedCarapaceLength The curved carapace length.
 * @param invalidCurvedCarapaceLengthMessage A message indicating an invalid curved carapace length.
 * @param curvedCarapaceWidth The curved carapace width.
 * @param invalidCurvedCarapaceWidthMessage A message indicating an invalid curved carapace width.
 * @param damageFlippersHead Damage found on the flippers or head.
 * @param invalidDamageFlippersHeadMessage A message indicating invalid input for flippers or head damage.
 * @param damageCarapace Damage found on the carapace.
 * @param invalidDamageCarapaceMessage A message indicating invalid input for carapace damage.
 * @param nestEmergenceSwitch A Boolean representing the nesting emergence switch.
 * @param nestCode The code of the nest.
 * @param invalidNestCodeMessage A message indicating an invalid nest code.
 * @param depthTopEgg The depth to the top egg in the nest.
 * @param invalidDepthTopEggMessage A message indicating an invalid depth for the top egg.
 * @param depthBottomNests The depth to the bottom of the nest.
 * @param invalidDepthBottomNestMessage A message indicating an invalid depth for the bottom of the nest.
 * @param distanceToSea The distance from the nest to the sea.
 * @param invalidDistanceToSeaMessage A message indicating an invalid distance to the sea.
 * @param numEggsRelocated The number of eggs relocated.
 * @param invalidNumEggsRelocatedMessage A message indicating an invalid number of relocated eggs.
 * @param relocationComments Comments related to the relocation process.
 * @param invalidRelocationCommentsMessage A message indicating invalid input for relocation comments.
 * @param numEggsExcavated The number of eggs excavated.
 * @param invalidNumEggsExcavatedMessage A message indicating an invalid number of excavated eggs.
 * @param startLaying The start of laying time.
 * @param invalidStartLayingMessage A message indicating an invalid start of laying time.
 * @param startCover The start of covering time.
 * @param invalidStartCoverMessage A message indicating an invalid start of covering time.
 * @param startCamouflage The start of camouflage time.
 * @param invalidStartCamouflageMessage A message indicating an invalid start of camouflage time.
 * @param departNestSite The departure time from the nest site.
 * @param invalidDepartNestSiteMessage A message indicating an invalid departure time.
 * @param turtleAtSea The time that the turtle has returned to the sea.
 * @param invalidTurtleAtSeaMessage A message indicating an invalid return at sea time.
 * @param commentsRemarks General comments or remarks from the survey.
 * @param invalidCommentsRemarksMessage A message indicating invalid comments or remarks.
 * @property success Indicates whether the night survey entry can be submitted.
 * @property exception Any exception that might be thrown.
 */
data class NightSurveyState(
    val date: String = "",
    val invalidDateMessage: String = "",
    val area: String = "",
    val invalidAreaMessage: String = "",
    val beach: String = "",
    val invalidBeachMessage: String = "",
    val sector: String = "",
    val invalidSectorMessage: String = "",
    val subsector: String = "",
    val invalidSubsectorMessage: String = "",
    val tagger: String = "",
    val invalidTaggerMessage: String = "",
    val oldTagDataList: MutableList<OldTagData> = mutableListOf(),
    val newTagDataList: MutableList<NewTagData> = mutableListOf(),
    val lostTagScars: String = "",
    val invalidLostTagScarsMessage: String = "",
    val straightCarapaceLength: String = "",
    val invalidStraightCarapaceLengthMessage: String = "",
    val straightCarapaceWidth: String = "",
    val invalidStraightCarapaceWidthMessage: String = "",
    val curvedCarapaceLength: String = "",
    val invalidCurvedCarapaceLengthMessage: String = "",
    val curvedCarapaceWidth: String = "",
    val invalidCurvedCarapaceWidthMessage: String = "",
    val damageFlippersHead: String = "",
    val invalidDamageFlippersHeadMessage: String = "",
    val damageCarapace: String = "",
    val invalidDamageCarapaceMessage: String = "",
    val nestEmergenceSwitch: Boolean = false,
    val nestCode: String = "",
    val invalidNestCodeMessage: String = "",
    val depthTopEgg: String = "",
    val invalidDepthTopEggMessage: String = "",
    val depthBottomNests: String = "",
    val invalidDepthBottomNestMessage: String = "",
    val distanceToSea: String = "",
    val invalidDistanceToSeaMessage: String = "",
    val numEggsRelocated: String = "",
    val invalidNumEggsRelocatedMessage: String = "",
    val relocationComments: String = "",
    val invalidRelocationCommentsMessage: String = "",
    val numEggsExcavated: String = "",
    val invalidNumEggsExcavatedMessage: String = "",
    val startLaying: String = "",
    val invalidStartLayingMessage: String = "",
    val startCover: String = "",
    val invalidStartCoverMessage: String = "",
    val startCamouflage: String = "",
    val invalidStartCamouflageMessage: String = "",
    val departNestSite: String = "",
    val invalidDepartNestSiteMessage: String = "",
    val turtleAtSea: String = "",
    val invalidTurtleAtSeaMessage: String = "",
    val commentsRemarks: String = "",
    val invalidCommentsRemarksMessage: String = "",
    val success: Boolean = false,
    val exception: Exception? = null
) {

    /**
     * Transforms the current state into a NightSurveyModel.
     *
     * @return A NightSurveyModel object containing the transformed data.
     */
    fun transformData(): NightSurveyModel {
        return NightSurveyModel(
            date = date,
            area = area,
            beach = beach,
            sector = sector,
            subsector = subsector,
            tagger = tagger,
            oldTagDataList = oldTagDataList.map { item ->
                item.transformData()
            },
            newTagDataList = newTagDataList.map { item ->
                item.transformData()
            },
            lostTagScars = lostTagScars,
            straightCarapaceLength = straightCarapaceLength.toDouble(),
            straightCarapaceWidth = straightCarapaceWidth.toDouble(),
            curvedCarapaceLength = curvedCarapaceLength.toDouble(),
            curvedCarapaceWidth = curvedCarapaceWidth.toDouble(),
            damageFlippersHead = damageFlippersHead,
            damageCarapace = damageCarapace,
            nestEmergenceSwitch = nestEmergenceSwitch,
            nestCode = if (nestEmergenceSwitch) nestCode else null,
            depthTopEgg = if (nestEmergenceSwitch) depthTopEgg.toDouble() else null,
            depthBottomNests = if (nestEmergenceSwitch) depthBottomNests.toDouble() else null,
            distanceToSea = if (nestEmergenceSwitch) distanceToSea.toDouble() else null,
            numEggsRelocated = if (nestEmergenceSwitch) numEggsRelocated.toInt() else null,
            relocationComments = if (nestEmergenceSwitch) relocationComments else null,
            numEggsExcavated = if (nestEmergenceSwitch) numEggsExcavated.toInt() else null,
            startLaying = if (nestEmergenceSwitch) startLaying else null,
            startCover = if (nestEmergenceSwitch) startCover else null,
            startCamouflage = if (nestEmergenceSwitch) startCamouflage else null,
            departNestSite = departNestSite,
            turtleAtSea = turtleAtSea,
            commentsRemarks = commentsRemarks
        )
    }
}

/**
 * Represents the data related to an old tag.
 *
 * @param selectedOldTagLocation The location where the old tag is placed on the turtle.
 * @param invalidOldTagLocationMessage A message indicating an invalid old tag location.
 * @param oldTagCode The code of the old tag.
 * @param invalidOldTagCodeMessage A message indicating an invalid old tag code.
 * @param isTurtleSafeSwitchChecked A Boolean representing whether the TurtleSafe switch is checked.
 * @param tagNotes Additional notes related to the old tag.
 * @param invalidTagNotesMessage A message indicating invalid input for tag notes.
 */
data class OldTagData(
    val selectedOldTagLocation: String = "",
    val invalidOldTagLocationMessage: String = "",
    val oldTagCode: String = "",
    val invalidOldTagCodeMessage: String = "",
    val isTurtleSafeSwitchChecked: Boolean = true,
    val tagNotes: String = "",
    val invalidTagNotesMessage: String = ""
) {

    /**
     * Transforms the current state into an OldTagDataModel.
     *
     * @return An OldTagDataModel object containing the transformed data.
     */
    fun transformData(): OldTagDataModel {
        return OldTagDataModel(
            selectedOldTagLocation = when (selectedOldTagLocation) {
                "FL: Front Left Flipper" -> "FL"
                "FR: Front Right Flipper" -> "FR"
                "HL: Hind Left Flipper" -> "HL"
                "HR: Hind Right Flipper" -> "HR"
                else -> ""
            },
            oldTagCode = oldTagCode,
            isTurtleSafeSwitchChecked = isTurtleSafeSwitchChecked,
            tagNotes = if (!isTurtleSafeSwitchChecked) tagNotes else null
        )
    }
}

/**
 * Represents the data related to a new tag.
 *
 * @param selectedNewTagLocation The location where the new tag is placed on the turtle.
 * @param invalidNewTagLocationMessage A message indicating an invalid new tag location.
 * @param newTagCode The code of the new tag.
 * @param invalidNewTagCodeMessage A message indicating an invalid new tag code.
 * @param isTagSwitchChecked A Boolean representing whether the tag switch is checked.
 * @param selectedNewScarType The type of new scar associated with the tagging process.
 * @param invalidNewScarTypeMesssage A message indicating an invalid new scar type.
 * @param newScarLocation The location of the new scar on the turtle.
 * @param invalidNewScarLocationMessage A message indicating an invalid new scar location.
 */
data class NewTagData(
    val selectedNewTagLocation: String = "",
    val invalidNewTagLocationMessage: String = "",
    val newTagCode: String = "",
    val invalidNewTagCodeMessage: String = "",
    val isTagSwitchChecked: Boolean = true,
    val selectedNewScarType: String = "",
    val invalidNewScarTypeMesssage: String = "",
    val newScarLocation: String = "",
    val invalidNewScarLocationMessage: String = ""
) {

    /**
     * Transforms the current state into a NewTagDataModel.
     *
     * @return A NewTagDataModel object containing the transformed data.
     */
    fun transformData(): NewTagDataModel {
        return NewTagDataModel(
            selectedNewTagLocation = when (selectedNewTagLocation) {
                "FL: Front Left Flipper" -> "FL"
                "FR: Front Right Flipper" -> "FR"
                "HL: Hind Left Flipper" -> "HL"
                "HR: Hind Right Flipper" -> "HR"
                else -> ""
            },
            newTagCode = newTagCode,
            isTagSwitchChecked = isTagSwitchChecked,
            selectedNewScarType = if (!isTagSwitchChecked) selectedNewScarType else null,
            newScarLocation = if (!isTagSwitchChecked) newScarLocation else null
        )
    }
}