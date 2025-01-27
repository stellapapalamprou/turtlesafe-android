package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.spapalamprou.turtlesafe.domain.models.HatchingDataModel
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel

/**
 * Entity representing a nest hatching record in the database.
 *
 * @property id The unique ID of the nest hatching record, automatically generated.
 * @property morningSurveyId The ID of the associated morning survey, linking this hatching to a specific survey.
 * @property nestCode A code representing the specific nest being hatched.
 * @property firstDayHatching The date when hatching first occurred.
 * @property lastDayHatching The date when hatching last occurred.
 * @property commentsOrRemarks Any additional comments or remarks regarding the hatching.
 */
@Entity
data class NestHatchingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "morning_survey_id") val morningSurveyId: Long,
    @ColumnInfo(name = "nest_code") val nestCode: String,
    @ColumnInfo(name = "first_day_hatching") val firstDayHatching: String,
    @ColumnInfo(name = "last_day_hatching") val lastDayHatching: String,
    @ColumnInfo(name = "comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Converts a NestHatchingModel object to a NestHatchingEntity.
 *
 * @param morningSurveyId The ID of the morning survey associated with this hatching.
 * @return A new NestHatchingEntity object containing the data from the NestHatchingModel.
 */
fun NestHatchingModel.asEntity(morningSurveyId: Long): NestHatchingEntity {
    return NestHatchingEntity(
        morningSurveyId = morningSurveyId,
        nestCode = nestCode,
        firstDayHatching = firstDayHatching,
        lastDayHatching = lastDayHatching,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Entity representing a specific hatching event associated with a nest hatching record.
 *
 * @property id The unique ID of the survey, automatically generated.
 * @property nestHatchingId The ID of the associated nest hatching, linking this event to a specific hatching record.
 * @property selectedHatchingEvent The selected hatching event.
 */
@Entity
data class HatchingDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "nest_hatching_id") var nestHatchingId: Long = 0,
    @ColumnInfo(name = "selected_hatching_event") val selectedHatchingEvent: String
)

/**
 * Converts a HatchingDataModel object to a HatchingDataEntity.
 *
 * @return A new HatchingDataEntity object containing the data from the HatchingDataModel.
 */
fun HatchingDataModel.asEntity(): HatchingDataEntity {
    return HatchingDataEntity(
        selectedHatchingEvent = selectedHatchingEvent
    )
}


/**
 * Converts a HatchingDataEntity object to a HatchingDataModel.
 *
 * @return A new HatchingDataModel object containing data from the HatchingDataEntity.
 */
fun HatchingDataEntity.asModel(): HatchingDataModel {
    return HatchingDataModel(
        selectedHatchingEvent = selectedHatchingEvent
    )
}

/**
 * Represents a nest hatching record along with its associated hatching events.
 *
 * @property nestHatchingEntity The nest hatching entity containing basic information about the hatching.
 * @property hatchingEvents A list of associated hatching data entities representing individual hatching events.
 */
data class NestHatchingWithEvents(
    @Embedded
    val nestHatchingEntity: NestHatchingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "nest_hatching_id",
    )
    val hatchingEvents: List<HatchingDataEntity>
)

/**
 * Converts a NestHatchingWithEvents object to a NestHatchingModel.
 *
 * @return A new NestHatchingModel object containing data from the NestHatchingWithEvents entity.
 */
fun NestHatchingWithEvents.asModel(): NestHatchingModel {
    return NestHatchingModel(
        nestCode = nestHatchingEntity.nestCode,
        firstDayHatching = nestHatchingEntity.firstDayHatching,
        lastDayHatching = nestHatchingEntity.lastDayHatching,
        commentsOrRemarks = nestHatchingEntity.commentsOrRemarks,
        hatchingDataList = hatchingEvents.map { event ->
            event.asModel()
        }
    )
}

