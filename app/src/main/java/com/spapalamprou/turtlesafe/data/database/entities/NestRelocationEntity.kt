package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel

/**
 * Entity representing a record of nest relocation in the database.
 *
 * @property id The unique ID for the nest relocation record, automatically generated.
 * @property morningSurveyId The ID of the associated morning survey, linking this relocation to a specific survey.
 * @property oldNestCode The code representing the nest from which the eggs were relocated.
 * @property relocatedTo The new location where the nest has been relocated.
 * @property sector The sector where the new nest is located.
 * @property subsector The subsector where the new nest is located.
 * @property reasonForRelocation A description of the reason for relocating the nest.
 * @property newNestCode The code assigned to the new nest after relocation.
 * @property depthTopEgg The depth of the top egg in the nest.
 * @property depthBottomNest The depth of the bottom of the nest.
 * @property distanceToSea The distance from the nest to the sea.
 * @property numOfEggsTransplanted The number of eggs that were transplanted to the new nest.
 * @property commentsOrRemarks Any additional comments or remarks regarding the relocation event.
 */
@Entity
data class NestRelocationEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "morning_survey_id") val morningSurveyId: Long,
    @ColumnInfo(name = "old_nest_code") val oldNestCode: String,
    @ColumnInfo(name = "relocated_to") val relocatedTo: String,
    @ColumnInfo(name = "sector") val sector: String,
    @ColumnInfo(name = "subsector") val subsector: String,
    @ColumnInfo(name = "reason_for_relocation") val reasonForRelocation: String,
    @ColumnInfo(name = "new_nest_code") val newNestCode: String,
    @ColumnInfo(name = "depth_top_egg") val depthTopEgg: Double,
    @ColumnInfo(name = "depth_bottom_nest") val depthBottomNest: Double,
    @ColumnInfo(name = "distance_to_sea") val distanceToSea: Double,
    @ColumnInfo(name = "number_of_eggs_transplanted") val numOfEggsTransplanted: Int,
    @ColumnInfo(name = "comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Converts a NestRelocationModel object to a NestRelocationEntity.
 *
 * @param morningSurveyId The ID of the morning survey associated with this relocation.
 * @return A new NestRelocationEntity object containing data from the NestRelocationModel.
 */
fun NestRelocationModel.asEntity(morningSurveyId: Long): NestRelocationEntity {
    return NestRelocationEntity(
        morningSurveyId = morningSurveyId,
        oldNestCode = oldNestCode,
        relocatedTo = relocatedTo,
        sector = sector,
        subsector = subsector,
        reasonForRelocation = reasonForRelocation,
        newNestCode = newNestCode,
        depthTopEgg = depthTopEgg,
        depthBottomNest = depthBottomNest,
        distanceToSea = distanceToSea,
        numOfEggsTransplanted = numOfEggsTransplanted,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Converts a NestRelocationEntity object to a NestRelocationModel.
 *
 * @return A new NestRelocationModel object containing data from the NestRelocationEntity.
 */
//for API
fun NestRelocationEntity.asModel(): NestRelocationModel {
    return NestRelocationModel(
        oldNestCode = oldNestCode,
        relocatedTo = relocatedTo,
        sector = sector,
        subsector = subsector,
        reasonForRelocation = reasonForRelocation,
        newNestCode = newNestCode,
        depthTopEgg = depthTopEgg,
        depthBottomNest = depthBottomNest,
        distanceToSea = distanceToSea,
        numOfEggsTransplanted = numOfEggsTransplanted,
        commentsOrRemarks = commentsOrRemarks
    )
}
