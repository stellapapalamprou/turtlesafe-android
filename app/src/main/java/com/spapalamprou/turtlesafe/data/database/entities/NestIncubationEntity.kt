package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.spapalamprou.turtlesafe.domain.models.IncubationDataModel
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel

/**
 * Entity representing a nest incubation record in the database.
 *
 * @property id The unique ID of the nest incubation record, automatically generated.
 * @property morningSurveyId The ID of the associated morning survey, linking this incubation to a specific survey.
 * @property nestCode A code representing the specific nest being incubated.
 * @property nestLocation The location of the nest.
 * @property hatcheryCode A code representing a hatchery, in case the nest is incubating in a hatchery.
 * @property protectedNestSwitch A boolean indicating whether the nest is protected or not.
 * @property protectionMeasures A description of the protection measures placed for the nest.
 * @property commentsOrRemarks Any additional comments or remarks regarding the incubation.
 */
@Entity
data class NestIncubationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "morning_survey_id") val morningSurveyId: Long,
    @ColumnInfo(name = "nest_code") val nestCode: String,
    @ColumnInfo(name = "nest_location") val nestLocation: String,
    @ColumnInfo(name = "hatchery_code") val hatcheryCode: String?,
    @ColumnInfo(name = "protected_nest_switch") val protectedNestSwitch: Boolean,
    @ColumnInfo(name = "protection_measures") val protectionMeasures: String,
    @ColumnInfo(name = "comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Converts a NestIncubationModel object to a NestIncubationEntity.
 *
 * @param morningSurveyId The ID of the morning survey associated with this incubation.
 * @return A new NestIncubationEntity object containing data from the NestIncubationModel.
 */
fun NestIncubationModel.asEntity(morningSurveyId: Long): NestIncubationEntity {
    return NestIncubationEntity(
        morningSurveyId = morningSurveyId,
        nestCode = nestCode,
        nestLocation = nestLocation,
        hatcheryCode = hatcheryCode,
        protectedNestSwitch = protectedNestSwitch,
        protectionMeasures = protectionMeasures,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Entity representing a specific incubation event associated with a nest incubation record.
 *
 * @property id The unique identifier for the incubation data entry, automatically generated.
 * @property nestIncubationId The ID of the associated nest incubation, linking this event to a specific incubation record.
 * @property selectedIncubationEvent The incubation event selected.
 */
@Entity
data class IncubationDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "nest_incubation_id") var nestIncubationId: Long = 0,
    @ColumnInfo(name = "selected_incubation_event") val selectedIncubationEvent: String
)

/**
 * Converts an IncubationDataModel object to an IncubationDataEntity.
 *
 * @return A new IncubationDataEntity object containing data from the IncubationDataModel.
 */
fun IncubationDataModel.asEntity(): IncubationDataEntity {
    return IncubationDataEntity(
        selectedIncubationEvent = selectedIncubationEvent
    )
}

/**
 * Converts an IncubationDataEntity object to an IncubationDataModel.
 *
 * @return A new IncubationDataModel object containing data from the IncubationDataEntity.
 */
fun IncubationDataEntity.asModel(): IncubationDataModel {
    return IncubationDataModel(
        selectedIncubationEvent = selectedIncubationEvent
    )
}

/**
 * Represents a nest incubation record along with its associated incubation events.
 *
 * @property nestIncubationEntity The nest incubation entity containing basic information about the incubation.
 * @property incubationEvents A list of associated incubation data entities representing individual incubation events.
 */
data class NestIncubationWithEvents(
    @Embedded
    val nestIncubationEntity: NestIncubationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "nest_incubation_id",
    )
    val incubationEvents: List<IncubationDataEntity>
)

/**
 * Converts a NestIncubationWithEvents object to a NestIncubationModel.
 *
 * @return A new NestIncubationModel object containing data from the NestIncubationWithEvents entity.
 */
fun NestIncubationWithEvents.asModel(): NestIncubationModel {
    return NestIncubationModel(
        nestCode = nestIncubationEntity.nestCode,
        nestLocation = nestIncubationEntity.nestLocation,
        hatcheryCode = nestIncubationEntity.hatcheryCode,
        incubationDataList = incubationEvents.map { event ->
            event.asModel()
        },
        protectedNestSwitch = nestIncubationEntity.protectedNestSwitch,
        protectionMeasures = nestIncubationEntity.protectionMeasures,
        commentsOrRemarks = nestIncubationEntity.commentsOrRemarks
    )
}