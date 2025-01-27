package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.NewTagDataModel
import com.spapalamprou.turtlesafe.domain.models.NightSurveyModel
import com.spapalamprou.turtlesafe.domain.models.OldTagDataModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface representing the API for submitting a night survey.
 */
interface NightSurveyApi {

    /**
     * Sends a request to the server to create a new night survey.
     *
     * @param json The NightSurveyJson containing the data for the night survey.
     * @return The NightSurveyJsonResponse containing the response details of the created night survey.
     */
    @POST("api/night-surveys")
    suspend fun createNightSurvey(@Body json: NightSurveyJson): NightSurveyJsonResponse
}

/**
 * Data class representing the request body for a night survey.
 *
 * @property date The date of the night survey.
 * @property area The area where the night survey was conducted.
 * @property beach The beach where the night survey was conducted.
 * @property sector The sector of the night survey location.
 * @property subsector The subsector of the night survey location.
 * @property tagger The name of the person responsible to tag the turtles.
 * @property oldTagDataList A list of OldTagDataJson representing the old tags data.
 * @property newTagDataList A list of NewTagDataJson representing the new tags data.
 * @property lostTagScars Any scars from lost tags.
 * @property straightCarapaceLength The straight carapace length of the turtle.
 * @property straightCarapaceWidth The straight carapace width of the turtle.
 * @property curvedCarapaceLength The curved carapace length of the turtle.
 * @property curvedCarapaceWidth The curved carapace width of the turtle.
 * @property damageFlippersHead Any damage observed on flippers or head.
 * @property damageCarapace Any damage observed on the carapace.
 * @property nestEmergenceSwitch Boolean to indicate whether there was a nest emergence.
 * @property nestCode The code of the nest.
 * @property depthTopEgg The depth from the surface to the top egg.
 * @property depthBottomNests The depth from the top to the bottom of the nest.
 * @property distanceToSea The distance from the nest to the sea (in meters).
 * @property numEggsRelocated The number of eggs relocated.
 * @property relocationComments Any comments or remarks about the relocation process.
 * @property numEggsExcavated The number of eggs excavated.
 * @property startLaying The time when the turtle started laying eggs.
 * @property startCover The time when the turtle started covering the nest.
 * @property startCamouflage The time when the turtle started camouflaging the nest.
 * @property departNestSite The time when the turtle departed from the nest site.
 * @property turtleAtSea The time when the turtle reached the sea.
 * @property commentsRemarks Any additional comments or remarks.
 */
data class NightSurveyJson(
    @SerializedName("date") val date: String,
    @SerializedName("area") val area: String,
    @SerializedName("beach") val beach: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("subsector") val subsector: String,
    @SerializedName("tagger") val tagger: String,
    @SerializedName("old_tags") val oldTagDataList: List<OldTagDataJson>,
    @SerializedName("new_tags") val newTagDataList: List<NewTagDataJson>,
    @SerializedName("lost_tag_scars") val lostTagScars: String,
    @SerializedName("straight_carapace_length") val straightCarapaceLength: Double,
    @SerializedName("straight_carapace_width") val straightCarapaceWidth: Double,
    @SerializedName("curved_carapace_length") val curvedCarapaceLength: Double,
    @SerializedName("curved_carapace_width") val curvedCarapaceWidth: Double,
    @SerializedName("damage_flippers_head") val damageFlippersHead: String,
    @SerializedName("damage_carapace") val damageCarapace: String,
    @SerializedName("nest_emergence_switch") val nestEmergenceSwitch: Boolean,
    @SerializedName("nest_code") val nestCode: String?,
    @SerializedName("depth_top_egg") val depthTopEgg: Double?,
    @SerializedName("depth_bottom_nests") val depthBottomNests: Double?,
    @SerializedName("distance_to_sea") val distanceToSea: Double?,
    @SerializedName("number_of_eggs_relocated") val numEggsRelocated: Int?,
    @SerializedName("relocation_comments") val relocationComments: String?,
    @SerializedName("number_of_eggs_excavated") val numEggsExcavated: Int?,
    @SerializedName("start_laying") val startLaying: String?,
    @SerializedName("start_cover") val startCover: String?,
    @SerializedName("start_camouflage") val startCamouflage: String?,
    @SerializedName("depart_nest_site") val departNestSite: String?,
    @SerializedName("turtle_at_sea") val turtleAtSea: String?,
    @SerializedName("comments_remarks") val commentsRemarks: String
)

/**
 * Data class representing the response body for a night survey.
 *
 * @property id The unique id of the night survey.
 * @property date The date of the night survey.
 * @property area The area where the night survey was conducted.
 * @property beach The beach where the night survey was conducted.
 * @property sector The sector of the night survey location.
 * @property subsector The subsector of the night survey location.
 * @property tagger The name of the person responsible to tag the turtles.
 * @property oldTagDataList A list of OldTagDataJson representing the old tags data.
 * @property newTagDataList A list of NewTagDataJson representing the new tags data.
 * @property lostTagScars Any scars from lost tags.
 * @property straightCarapaceLength The straight carapace length of the turtle.
 * @property straightCarapaceWidth The straight carapace width of the turtle.
 * @property curvedCarapaceLength The curved carapace length of the turtle.
 * @property curvedCarapaceWidth The curved carapace width of the turtle.
 * @property damageFlippersHead Any damage observed on flippers or head.
 * @property damageCarapace Any damage observed on the carapace.
 * @property nestEmergenceSwitch Boolean to indicate whether there was a nest emergence.
 * @property nestCode The code of the nest.
 * @property depthTopEgg The depth from the surface to the top egg.
 * @property depthBottomNests The depth from the top to the bottom of the nest.
 * @property distanceToSea The distance from the nest to the sea (in meters).
 * @property numEggsRelocated The number of eggs relocated.
 * @property relocationComments Any comments or remarks about the relocation process.
 * @property numEggsExcavated The number of eggs excavated.
 * @property startLaying The time when the turtle started laying eggs.
 * @property startCover The time when the turtle started covering the nest.
 * @property startCamouflage The time when the turtle started camouflaging the nest.
 * @property departNestSite The time when the turtle departed from the nest site.
 * @property turtleAtSea The time when the turtle reached the sea.
 * @property commentsRemarks Any additional comments or remarks.
 */
data class NightSurveyJsonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("area") val area: String,
    @SerializedName("beach") val beach: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("subsector") val subsector: String,
    @SerializedName("tagger") val tagger: String,
    @SerializedName("old_tags") val oldTagDataList: List<OldTagDataJsonResponse>,
    @SerializedName("new_tags") val newTagDataList: List<NewTagDataJsonResponse>,
    @SerializedName("lost_tag_scars") val lostTagScars: String,
    @SerializedName("straight_carapace_length") val straightCarapaceLength: Double,
    @SerializedName("straight_carapace_width") val straightCarapaceWidth: Double,
    @SerializedName("curved_carapace_length") val curvedCarapaceLength: Double,
    @SerializedName("curved_carapace_width") val curvedCarapaceWidth: Double,
    @SerializedName("damage_flippers_head") val damageFlippersHead: String,
    @SerializedName("damage_carapace") val damageCarapace: String,
    @SerializedName("nest_emergence_switch") val nestEmergenceSwitch: Boolean,
    @SerializedName("nest_code") val nestCode: String?,
    @SerializedName("depth_top_egg") val depthTopEgg: Double?,
    @SerializedName("depth_bottom_nests") val depthBottomNests: Double?,
    @SerializedName("distance_to_sea") val distanceToSea: Double?,
    @SerializedName("number_of_eggs_relocated") val numEggsRelocated: Int?,
    @SerializedName("relocation_comments") val relocationComments: String?,
    @SerializedName("number_of_eggs_excavated") val numEggsExcavated: Int?,
    @SerializedName("start_laying") val startLaying: String?,
    @SerializedName("start_cover") val startCover: String?,
    @SerializedName("start_camouflage") val startCamouflage: String?,
    @SerializedName("depart_nest_site") val departNestSite: String?,
    @SerializedName("turtle_at_sea") val turtleAtSea: String?,
    @SerializedName("comments_remarks") val commentsRemarks: String
)

/**
 * Extension function to serialize a NightSurveyModel to a NightSurveyJson format.
 *
 * It also serializes nested objects of type OldTagDataModel
 * and NewTagDataModel to their JSON format.
 *
 * @return A NightSurveyJson object with the converted data.
 */
fun NightSurveyModel.asJson(): NightSurveyJson {
    return NightSurveyJson(
        date = date.changeDateFormat(),
        area = area,
        beach = beach,
        sector = sector,
        subsector = subsector,
        tagger = tagger,
        oldTagDataList = oldTagDataList.map { tag ->
            tag.asJson()
        },
        newTagDataList = newTagDataList.map { tag ->
            tag.asJson()
        },
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
        startLaying = startLaying?.changeTimeFormatNullable(),
        startCover = startCover?.changeTimeFormatNullable(),
        startCamouflage = startCamouflage?.changeTimeFormatNullable(),
        departNestSite = departNestSite.changeTimeFormatNullable(),
        turtleAtSea = turtleAtSea.changeTimeFormatNullable(),
        commentsRemarks = commentsRemarks
    )
}

/**
 * Data class representing the JSON structure for old tag data in a night survey.
 *
 * @property selectedOldTagLocation The location where the old tag was found.
 * @property oldTagCode The code of the old tag.
 * @property isTurtleSafeSwitchChecked Indicates whether the tag is an TurtleSafe tag.
 * @property tagNotes Any additional notes regarding the old tag.
 */
data class OldTagDataJson(
    @SerializedName("selected_old_tag_location") val selectedOldTagLocation: String,
    @SerializedName("old_tag_code") val oldTagCode: String,
    @SerializedName("is_turtlesafe_tag") val isTurtleSafeSwitchChecked: Boolean,
    @SerializedName("tag_notes") val tagNotes: String
)

/**
 * Data class representing the server's response for old tag data in a night survey.
 *
 * @property id The unique id for the old tag data.
 * @property selectedOldTagLocation The location where the old tag was found.
 * @property oldTagCode The code of the old tag.
 * @property isTurtleSafeSwitchChecked Indicates whether the tag is an TurtleSafe tag.
 * @property tagNotes Any additional notes regarding the old tag.
 */
data class OldTagDataJsonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("selected_old_tag_location") val selectedOldTagLocation: String,
    @SerializedName("old_tag_code") val oldTagCode: String,
    @SerializedName("is_turtlesafe_tag") val isTurtleSafeSwitchChecked: Boolean,
    @SerializedName("tag_notes") val tagNotes: String
)

/**
 * Extension function to serialize an OldTagDataModel to an OldTagDataJson format.
 *
 * @return An OldTagDataJson object with the converted data.
 */
fun OldTagDataModel.asJson(): OldTagDataJson {
    return OldTagDataJson(
        selectedOldTagLocation = selectedOldTagLocation,
        oldTagCode = oldTagCode,
        isTurtleSafeSwitchChecked = isTurtleSafeSwitchChecked,
        tagNotes = tagNotes ?: ""
    )
}

/**
 * Data class representing the JSON structure for new tag data in a night survey.
 *
 * @property selectedNewTagLocation The location where the new tag was applied.
 * @property newTagCode The code of the new tag.
 * @property isTagSwitchChecked Indicates whether the tagging was successful.
 * @property selectedNewScarType The type of scar that was created while placing the new tag.
 * @property newScarLocation The location of the new scar on the turtle.
 */
data class NewTagDataJson(
    @SerializedName("selected_new_tag_location") val selectedNewTagLocation: String,
    @SerializedName("new_tag_code") val newTagCode: String,
    @SerializedName("is_tagging_successful") val isTagSwitchChecked: Boolean,
    @SerializedName("selected_new_scar_type") val selectedNewScarType: String,
    @SerializedName("new_scar_location") val newScarLocation: String
)

/**
 * Data class representing the server's response for new tag data in a night survey.
 *
 * @property id The unique id for the new tag data.
 * @property selectedNewTagLocation The location where the new tag was applied.
 * @property newTagCode The code of the new tag.
 * @property isTaggingSuccessful Indicates whether the tagging was successful.
 * @property selectedNewScarType The type of scar that was created while placing the new tag.
 * @property newScarLocation The location of the new scar on the turtle.
 */
data class NewTagDataJsonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("selected_new_tag_location") val selectedNewTagLocation: String,
    @SerializedName("new_tag_code") val newTagCode: String,
    @SerializedName("is_tagging_successful") val isTagSwitchChecked: Boolean,
    @SerializedName("selected_new_scar_type") val selectedNewScarType: String,
    @SerializedName("new_scar_location") val newScarLocation: String
)

/**
 * An extension function that serializes a NewTagDataModel to a NewTagDataJson format.
 *
 * @return A NewTagDataJson object with the converted data.
 */
fun NewTagDataModel.asJson(): NewTagDataJson {
    return NewTagDataJson(
        selectedNewTagLocation = selectedNewTagLocation,
        newTagCode = newTagCode,
        isTagSwitchChecked = isTagSwitchChecked,
        selectedNewScarType = selectedNewScarType ?: "",
        newScarLocation = newScarLocation ?: ""
    )
}