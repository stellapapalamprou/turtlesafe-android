package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.HatchingDataModel
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel
import com.google.gson.annotations.SerializedName

/**
 * Data class representing the hatching details of a nest.
 *
 * @property nestCode The unique nest code.
 * @property firstDayHatching The first day of hatching or `null` if not provided.
 * @property lastDayHatching The last day of hatching or `null` if not provided.
 * @property hatchingDataList A list of hatching events associated with this nest.
 * @property commentsOrRemarks Comments or remarks about the nest hatching entry.
 */
data class NestHatchingJson(
    @SerializedName("nest_code") val nestCode: String,
    @SerializedName("first_day_hatching") val firstDayHatching: String?,
    @SerializedName("last_day_hatching") val lastDayHatching: String?,
    @SerializedName("hatching_events") val hatchingDataList: List<HatchingDataJson>,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Extension function to serialize a NestHatchingModel to a NestHatchingJson format.
 *
 * Dates are formatted to "yyyy-MM-dd" and empty strings are converted to `null`.
 *
 * @return The NestHatchingJson representation of the NestHatchingModel.
 */
fun NestHatchingModel.asJson(): NestHatchingJson {
    return NestHatchingJson(
        nestCode = nestCode,
        firstDayHatching = if (firstDayHatching.isEmpty()) null else firstDayHatching.changeDateFormat(),
        lastDayHatching = if (lastDayHatching.isEmpty()) null else lastDayHatching.changeDateFormat(),
        hatchingDataList = hatchingDataList.map { event ->
            event.asJson()
        },
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Data class representing hatching events.
 *
 * @property selectedHatchingEvent The specific hatching event selected.
 */
data class HatchingDataJson(
    @SerializedName("selected_hatching_event") val selectedHatchingEvent: String
)

/**
 * Extension function to serialize a HatchingDataModel to a HatchingDataJson format.
 *
 * @return The HatchingDataJson representation of the HatchingDataModel.
 */
fun HatchingDataModel.asJson(): HatchingDataJson {
    return HatchingDataJson(
        selectedHatchingEvent = selectedHatchingEvent
    )
}