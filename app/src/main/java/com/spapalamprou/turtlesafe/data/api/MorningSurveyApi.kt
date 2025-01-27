package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface representing the API for submitting a morning survey.
 */
interface MorningSurveyApi {

    /**
     * Endpoint to submit a morning survey.
     *
     * @param json The morning survey data in json format.
     * @return MorningSurveyJsonResponse containing the survey's response data.
     */
    @POST("api/morning-surveys")
    suspend fun createMorningSurvey(@Body json: MorningSurveyJson): MorningSurveyJsonResponse
}

/**
 * Data class representing the request body for creating a morning survey.
 *
 * @property date The date of the morning survey.
 * @property time The time of the morning survey.
 * @property area The area where the morning survey was conducted.
 * @property beach The beach where the morning survey was conducted.
 * @property nestingAttemptSwitch Boolean indicating if nesting attempts were observed.
 * @property numberOfAttempts The number of nesting attempts observed.
 * @property commentsOrRemarks Any comments or remarks about the morning survey.
 * @property newNestEntries List of new nest entries observed during the morning survey.
 * @property incubationEntries List of incubation-related entries for the morning survey.
 * @property hatchingEntries List of hatching-related entries for the morning survey.
 * @property excavationEntries List of excavation-related entries for the morning survey.
 * @property relocationEntries List of relocation-related entries for the morning survey.
 */
data class MorningSurveyJson(
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("area") val area: String,
    @SerializedName("beach") val beach: String,
    @SerializedName("nesting_attempt_switch") val nestingAttemptSwitch: Boolean,
    @SerializedName("number_of_attempts") val numberOfAttempts: Int,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String,
    @SerializedName("new_nests") val newNestEntries: List<NewNestJson>,
    @SerializedName("incubation_entries") val incubationEntries: List<NestIncubationJson>,
    @SerializedName("hatching_entries") val hatchingEntries: List<NestHatchingJson>,
    @SerializedName("excavation_entries") val excavationEntries: List<NestExcavationJson>,
    @SerializedName("relocation_entries") val relocationEntries: List<NestRelocationJson>
)

/**
 * Extension function to serialize a MorningSurveyModel to a MorningSurveyJson format.
 *
 * @return The MorningSurveyJson representation of the MorningSurveyModel.
 */
fun MorningSurveyModel.asJson(): MorningSurveyJson {
    return MorningSurveyJson(
        date = date.changeDateFormat(),
        time = time.changeTimeFormat(),
        area = area,
        beach = beach,
        nestingAttemptSwitch = nestingAttemptSwitch,
        numberOfAttempts = numberOfAttempts ?: 0,
        commentsOrRemarks = commentsOrRemarks,
        newNestEntries = newNestList.map { entry ->
            entry.asJson()
        },
        incubationEntries = nestIncubationList.map { entry ->
            entry.asJson()
        },
        hatchingEntries = nestHatchingList.map { entry ->
            entry.asJson()
        },
        excavationEntries = nestExcavationList.map { entry ->
            entry.asJson()
        },
        relocationEntries = nestRelocationList.map { entry ->
            entry.asJson()
        }
    )
}

/**
 * Data class representing the response for a submitted morning survey.
 *
 * @property id The unique id of the morning survey.
 * @property date The date of the morning survey.
 * @property time The time of the morning survey.
 * @property area The area where the morning survey was conducted.
 * @property beach The beach where the morning survey was conducted.
 * @property nestingAttemptSwitch Boolean indicating if nesting attempts were observed.
 * @property numberOfAttempts The number of nesting attempts observed.
 * @property commentsOrRemarks Any comments or remarks about the morning survey.
 */
data class MorningSurveyJsonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("area") val area: String,
    @SerializedName("beach") val beach: String,
    @SerializedName("nesting_attempt_switch") val nestingAttemptSwitch: Boolean,
    @SerializedName("number_of_attempts") val numberOfAttempts: Int,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String
)