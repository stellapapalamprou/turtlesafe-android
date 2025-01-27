package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.spapalamprou.turtlesafe.domain.models.NewTagDataModel
import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.models.OldTagDataModel

/**
 * Entity representing a night survey record in the database.
 *
 * @property id The unique identifier for the night survey record, automatically generated.
 * @property date The date when the night survey was conducted.
 * @property area The area where the night survey took place.
 * @property beach The specific beach where the survey was conducted.
 * @property sector The sector where the survey was conducted.
 * @property subsector The subsector where the survey was conducted.
 * @property tagger The name of the individual conducting the survey.
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
@Entity
data class NightSurveyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "beach") val beach: String,
    @ColumnInfo(name = "sector") val sector: String,
    @ColumnInfo(name = "subsector") val subsector: String,
    @ColumnInfo(name = "tagger") val tagger: String,
    @ColumnInfo(name = "lost_tag_scars") val lostTagScars: String,
    @ColumnInfo(name = "straight_carapace_length") val straightCarapaceLength: Double,
    @ColumnInfo(name = "straight_carapace_width") val straightCarapaceWidth: Double,
    @ColumnInfo(name = "curved_carapace_length") val curvedCarapaceLength: Double,
    @ColumnInfo(name = "curved_carapace_width") val curvedCarapaceWidth: Double,
    @ColumnInfo(name = "damage_flippers_head") val damageFlippersHead: String,
    @ColumnInfo(name = "damage_carapace") val damageCarapace: String,
    @ColumnInfo(name = "nest_emergence_switch") val nestEmergenceSwitch: Boolean,
    @ColumnInfo(name = "nest_code") val nestCode: String?,
    @ColumnInfo(name = "depth_top_egg") val depthTopEgg: Double?,
    @ColumnInfo(name = "depth_bottom_nests") val depthBottomNests: Double?,
    @ColumnInfo(name = "distance_to_sea") val distanceToSea: Double?,
    @ColumnInfo(name = "number_of_eggs_relocated") val numEggsRelocated: Int?,
    @ColumnInfo(name = "relocation_comments") val relocationComments: String?,
    @ColumnInfo(name = "number_of_eggs_excavated") val numEggsExcavated: Int?,
    @ColumnInfo(name = "start_laying") val startLaying: String?,
    @ColumnInfo(name = "start_cover") val startCover: String?,
    @ColumnInfo(name = "start_camouflage") val startCamouflage: String?,
    @ColumnInfo(name = "depart_nest_site") val departNestSite: String,
    @ColumnInfo(name = "turtle_at_sea") val turtleAtSea: String,
    @ColumnInfo(name = "comments_remarks") val commentsRemarks: String
)

/**
 * Converts a NightSurveyModel object to a NightSurveyEntity.
 *
 * @return A new NightSurveyEntity object containing data from the NightSurveyModel.
 */
fun NightSurveyModel.asEntity(): NightSurveyEntity {
    return NightSurveyEntity(
        date = date,
        area = area,
        beach = beach,
        sector = sector,
        subsector = subsector,
        tagger = tagger,
        lostTagScars = lostTagScars,
        straightCarapaceLength = straightCarapaceLength,
        straightCarapaceWidth = straightCarapaceWidth,
        curvedCarapaceLength = curvedCarapaceLength,
        curvedCarapaceWidth = curvedCarapaceWidth,
        damageFlippersHead = damageFlippersHead,
        damageCarapace = damageCarapace,
        nestEmergenceSwitch = nestEmergenceSwitch,
        nestCode = nestCode,
        depthTopEgg = depthTopEgg,
        depthBottomNests = depthBottomNests,
        distanceToSea = distanceToSea,
        numEggsRelocated = numEggsRelocated,
        relocationComments = relocationComments,
        numEggsExcavated = numEggsExcavated,
        startLaying = startLaying,
        startCover = startCover,
        startCamouflage = startCamouflage,
        departNestSite = departNestSite,
        turtleAtSea = turtleAtSea,
        commentsRemarks = commentsRemarks
    )
}

/**
 * Entity representing old tag data associated with a night survey.
 *
 * @property id The unique identifier for the old tag data entry, automatically generated.
 * @property nightSurveyId The ID of the associated night survey, linking this old tag to a specific survey.
 * @property selectedOldTagLocation The turtle's body part where the old tag was found.
 * @property oldTagCode The code of the old tag.
 * @property isTurtleSafeSwitchChecked A boolean indicating whether the tag is an TurtleSafe tag.
 * @property tagNotes Any additional notes related to the old tag.
 */
@Entity
data class OldTagDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "night_survey_id") var nightSurveyId: Long = 0,
    @ColumnInfo(name = "selected_old_tag_location") val selectedOldTagLocation: String,
    @ColumnInfo(name = "old_tag_code") val oldTagCode: String,
    @ColumnInfo(name = "is_turtlesafe_tag") val isTurtleSafeSwitchChecked: Boolean,
    @ColumnInfo(name = "tag_notes") val tagNotes: String?
)

/**
 * Converts an OldTagDataModel object to an OldTagDataEntity.
 *
 * @return A new OldTagDataEntity object populated with data from the OldTagDataModel.
 */
fun OldTagDataModel.asEntity(): OldTagDataEntity {
    return OldTagDataEntity(
        selectedOldTagLocation = selectedOldTagLocation,
        oldTagCode = oldTagCode,
        isTurtleSafeSwitchChecked = isTurtleSafeSwitchChecked,
        tagNotes = tagNotes
    )
}

/**
 * Converts an OldTagDataEntity object to an OldTagDataModel.
 *
 * @return A new OldTagDataModel object containing data from the OldTagDataEntity.
 */
fun OldTagDataEntity.asModel(): OldTagDataModel {
    return OldTagDataModel(
        selectedOldTagLocation = selectedOldTagLocation,
        oldTagCode = oldTagCode,
        isTurtleSafeSwitchChecked = isTurtleSafeSwitchChecked,
        tagNotes = tagNotes
    )
}

/**
 * Entity representing new tag data associated with a night survey.
 *
 * @property id The unique identifier for the new tag data entry, automatically generated.
 * @property nightSurveyId The ID of the associated night survey, linking this new tag to a specific survey.
 * @property selectedNewTagLocation The turtle's body part where the new tag is applied.
 * @property newTagCode The code of the new tag.
 * @property isTagSwitchChecked A boolean indicating whether the tagging was successful.
 * @property selectedNewScarType The type of scar associated with the new tag, if applicable.
 * @property newScarLocation The location of the new scar, if applicable.
 */
@Entity
data class NewTagDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "night_survey_id") var nightSurveyId: Long = 0,
    @ColumnInfo(name = "selected_new_tag_location") val selectedNewTagLocation: String,
    @ColumnInfo(name = "new_tag_code") val newTagCode: String,
    @ColumnInfo(name = "is_tagging_successful") val isTagSwitchChecked: Boolean,
    @ColumnInfo(name = "selected_new_scar_type") val selectedNewScarType: String?,
    @ColumnInfo(name = "new_scar_location") val newScarLocation: String?
)

/**
 * Converts a NewTagDataModel object to a NewTagDataEntity.
 *
 * @return A new NewTagDataEntity object containing data from the NewTagDataModel.
 */
fun NewTagDataModel.asEntity(): NewTagDataEntity {
    return NewTagDataEntity(
        selectedNewTagLocation = selectedNewTagLocation,
        newTagCode = newTagCode,
        isTagSwitchChecked = isTagSwitchChecked,
        selectedNewScarType = selectedNewScarType,
        newScarLocation = newScarLocation
    )
}

/**
 * Converts a NewTagDataEntity object to a NewTagDataModel.
 *
 * @return A new NewTagDataModel object populated with data from the NewTagDataEntity.
 */
fun NewTagDataEntity.asModel(): NewTagDataModel {
    return NewTagDataModel(
        selectedNewTagLocation = selectedNewTagLocation,
        newTagCode = newTagCode,
        isTagSwitchChecked = isTagSwitchChecked,
        selectedNewScarType = selectedNewScarType,
        newScarLocation = newScarLocation
    )
}

/**
 * Data class representing a night survey along with its associated old and new tags.
 *
 * @property nightSurveyEntity The night survey entity containing the survey details.
 * @property oldTags A list of old tag data entities associated with the night survey.
 * @property newTags A list of new tag data entities associated with the night survey.
 */
data class NightSurveyWithTags(
    @Embedded
    val nightSurveyEntity: NightSurveyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "night_survey_id"
    )
    val oldTags: List<OldTagDataEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "night_survey_id"
    )
    val newTags: List<NewTagDataEntity>
)

/**
 * Converts a NightSurveyWithTags object to a NightSurveyModel for application use, and maps the old
 * and new tags to their associated models.
 *
 * @return A new NightSurveyModel object populated with data from the NightSurveyWithTags.
 */
fun NightSurveyWithTags.asModel(): NightSurveyModel {
    return NightSurveyModel(
        date = nightSurveyEntity.date,
        area = nightSurveyEntity.area,
        beach = nightSurveyEntity.beach,
        sector = nightSurveyEntity.sector,
        subsector = nightSurveyEntity.subsector,
        tagger = nightSurveyEntity.tagger,
        oldTagDataList = oldTags.map { tag ->
            tag.asModel()
        },
        newTagDataList = newTags.map { tag ->
            tag.asModel()
        },
        lostTagScars = nightSurveyEntity.lostTagScars,
        straightCarapaceLength = nightSurveyEntity.straightCarapaceLength,
        straightCarapaceWidth = nightSurveyEntity.straightCarapaceWidth,
        curvedCarapaceLength = nightSurveyEntity.curvedCarapaceLength,
        curvedCarapaceWidth = nightSurveyEntity.curvedCarapaceWidth,
        damageFlippersHead = nightSurveyEntity.damageFlippersHead,
        damageCarapace = nightSurveyEntity.damageCarapace,
        nestEmergenceSwitch = nightSurveyEntity.nestEmergenceSwitch,
        nestCode = nightSurveyEntity.nestCode,
        depthTopEgg = nightSurveyEntity.depthTopEgg,
        depthBottomNests = nightSurveyEntity.depthBottomNests,
        distanceToSea = nightSurveyEntity.distanceToSea,
        numEggsRelocated = nightSurveyEntity.numEggsRelocated,
        relocationComments = nightSurveyEntity.relocationComments,
        numEggsExcavated = nightSurveyEntity.numEggsExcavated,
        startLaying = nightSurveyEntity.startLaying,
        startCover = nightSurveyEntity.startCover,
        startCamouflage = nightSurveyEntity.startCamouflage,
        departNestSite = nightSurveyEntity.departNestSite,
        turtleAtSea = nightSurveyEntity.turtleAtSea,
        commentsRemarks = nightSurveyEntity.commentsRemarks
    )
}