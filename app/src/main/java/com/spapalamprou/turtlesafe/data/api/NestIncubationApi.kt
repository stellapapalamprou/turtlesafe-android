package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.IncubationDataModel
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel
import com.google.gson.annotations.SerializedName

/**
 * Data class representing the incubation details of a nest.
 *
 * @property nestCode The unique nest code.
 * @property nestLocation The location where the nest is located.
 * @property hatcheryCode The code identifying the hatchery where the nest is located, or an empty string if not applicable.
 * @property protectedNestSwitch Boolean indicating if the nest is protected.
 * @property protectionMeasures The protection measures applied to the nest.
 * @property incubationDataList A list of incubation events associated with this nest.
 * @property commentsOrRemarks Additional comments or remarks about the nest incubation.
 */
data class NestIncubationJson(
    @SerializedName("nest_code") val nestCode: String,
    @SerializedName("nest_location") val nestLocation: String,
    @SerializedName("hatchery_code") val hatcheryCode: String,
    @SerializedName("protected_nest_switch") val protectedNestSwitch: Boolean,
    @SerializedName("protection_measures") val protectionMeasures: String,
    @SerializedName("incubation_events") val incubationDataList: List<IncubationDataJson>,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Extension function to serialize a NestIncubationModel to a NestIncubationJson format.
 *
 * @return The NestIncubationJson representation of the NestIncubationModel.
 */
fun NestIncubationModel.asJson(): NestIncubationJson {
    return NestIncubationJson(
        nestCode = nestCode,
        nestLocation = nestLocation,
        hatcheryCode = hatcheryCode ?: "",
        incubationDataList = incubationDataList.map { event ->
            event.asJson()
        },
        protectedNestSwitch = protectedNestSwitch,
        protectionMeasures = protectionMeasures,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Data class representing incubation events.
 *
 * @property selectedIncubationEvent The specific incubation event selected.
 */
data class IncubationDataJson(
    @SerializedName("selected_incubation_event") val selectedIncubationEvent: String
)

/**
 * Extension function to serialize an IncubationDataModel to an IncubationDataJson format.
 *
 * @return The IncubationDataJson representation of the IncubationDataModel.
 */
fun IncubationDataModel.asJson(): IncubationDataJson {
    return IncubationDataJson(
        selectedIncubationEvent = selectedIncubationEvent
    )
}