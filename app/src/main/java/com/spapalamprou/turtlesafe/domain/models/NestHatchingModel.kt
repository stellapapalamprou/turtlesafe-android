package com.spapalamprou.turtlesafe.domain.models

/**
 * Model representing a nest hatching.
 *
 * @property nestCode A code representing the specific nest being hatched.
 * @property firstDayHatching The date when hatching first occurred.
 * @property lastDayHatching The date when hatching last occurred.
 * @property hatchingDataList A list of hatching events.
 * @property commentsOrRemarks Any additional comments or remarks regarding the excavation.
 */
data class NestHatchingModel(
    val nestCode: String,
    val firstDayHatching: String,
    val lastDayHatching: String,
    val hatchingDataList: List<HatchingDataModel>,
    val commentsOrRemarks: String
)

/**
 * Model representing a hatching event.
 *
 * @property selectedHatchingEvent The hatching event selected.
 */
data class HatchingDataModel(
    val selectedHatchingEvent: String
)