package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel

/**
 * Entity representing a morning survey in the database.
 *
 * @property id The unique ID of the survey, automatically generated.
 * @property date The date when the morning survey was conducted.
 * @property time The time when the morning survey was conducted.
 * @property area The area where the survey took place.
 * @property beach The beach where the survey was conducted.
 * @property nestingAttemptSwitch A boolean indicating whether nesting attempts were observed.
 * @property numberOfAttempts The number of nesting attempts recorded during the survey.
 * @property commentsOrRemarks Additional comments or remarks made during the survey.
 */
@Entity
data class MorningSurveyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "beach") val beach: String,
    @ColumnInfo(name = "nesting_attempt_switch") val nestingAttemptSwitch: Boolean,
    @ColumnInfo(name = "number_of_attempts") val numberOfAttempts: Int,
    @ColumnInfo(name = "comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Converts a MorningSurveyModel object to a MorningSurveyEntity.
 *
 * @return A new MorningSurveyEntity object containing the data from the MorningSurveyModel.
 */
fun MorningSurveyModel.asEntity(): MorningSurveyEntity {
    return MorningSurveyEntity(
        date = date,
        time = time,
        area = area,
        beach = beach,
        nestingAttemptSwitch = nestingAttemptSwitch,
        numberOfAttempts = numberOfAttempts ?: 0,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Data class representing a morning survey along with its associated activities.
 *
 * @property morningSurveyEntity The MorningSurveyEntity representing the survey data.
 * @property newNests A list of NewNestEntity objects representing new nests observed during the survey.
 * @property incubations A list of NestIncubationWithEvents objects representing incubations recorded during the survey.
 * @property relocations A list of NestRelocationEntity objects representing nest relocations recorded during the survey.
 * @property hatchings A list of NestHatchingWithEvents objects representing hatchings recorded during the survey.
 * @property excavations A list of NestExcavationEntity objects representing excavations recorded during the survey.
 */
data class MorningSurveyWithActivities(
    @Embedded
    val morningSurveyEntity: MorningSurveyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "morning_survey_id"
    )
    val newNests: List<NewNestEntity>,
    @Relation(
        entity = NestIncubationEntity::class,
        parentColumn = "id",
        entityColumn = "morning_survey_id"
    )
    val incubations: List<NestIncubationWithEvents>,
    @Relation(
        parentColumn = "id",
        entityColumn = "morning_survey_id"
    )
    val relocations: List<NestRelocationEntity>,
    @Relation(
        entity = NestHatchingEntity::class,
        parentColumn = "id",
        entityColumn = "morning_survey_id"
    )
    val hatchings: List<NestHatchingWithEvents>,
    @Relation(
        parentColumn = "id",
        entityColumn = "morning_survey_id"
    )
    val excavations: List<NestExcavationEntity>
)

/**
 * Converts a MorningSurveyWithActivities object to a MorningSurveyModel, and maps all associated
 * activities to their respective models.
 *
 * @return A new MorningSurveyModel object containing data from both the survey and its related activities.
 */
fun MorningSurveyWithActivities.asModel(): MorningSurveyModel {
    return MorningSurveyModel(
        date = morningSurveyEntity.date,
        time = morningSurveyEntity.time,
        area = morningSurveyEntity.area,
        beach = morningSurveyEntity.beach,
        newNestList = newNests.map { entry ->
            entry.asModel()
        },
        nestIncubationList = incubations.map { entry ->
            entry.asModel()
        },
        nestRelocationList = relocations.map { entry ->
            entry.asModel()
        },
        nestHatchingList = hatchings.map { entry ->
            entry.asModel()
        },
        nestExcavationList = excavations.map { entry ->
            entry.asModel()
        },
        nestingAttemptSwitch = morningSurveyEntity.nestingAttemptSwitch,
        numberOfAttempts = morningSurveyEntity.numberOfAttempts,
        commentsOrRemarks = morningSurveyEntity.commentsOrRemarks
    )
}

