package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel

/**
 * Entity representing a nest excavation record in the database.
 *
 * @property id The unique ID of the nest excavation record, automatically generated.
 * @property morningSurveyId The ID of the associated morning survey, linking this excavation to a specific survey.
 * @property nestCode A code representing the specific nest being excavated.
 * @property hatchedEggs The number of eggs that successfully hatched.
 * @property pippedDeadHatchlings The number of hatchlings that pipped but did not survive.
 * @property pippedLiveHatchlings The number of hatchlings that pipped and survived.
 * @property noEmbryos The number of eggs that did not have embryos.
 * @property deadEmbryos The number of dead embryos found.
 * @property liveEmbryos The number of live embryos found.
 * @property deadHatchlingsNest The number of dead hatchlings.
 * @property liveHatchlingsNest The number of live hatchlings.
 * @property commentsOrRemarks Any additional comments or remarks regarding the excavation.
 */
@Entity
data class NestExcavationEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "morning_survey_id") val morningSurveyId: Long,
    @ColumnInfo(name = "nest_code") val nestCode: String,
    @ColumnInfo(name = "hatched_eggs") val hatchedEggs: Int,
    @ColumnInfo(name = "pipped_dead_hatchlings") val pippedDeadHatchlings: Int,
    @ColumnInfo(name = "pipped_live_hatchlings") val pippedLiveHatchlings: Int,
    @ColumnInfo(name = "no_embryos") val noEmbryos: Int,
    @ColumnInfo(name = "dead_embryos") val deadEmbryos: Int,
    @ColumnInfo(name = "live_embryos") val liveEmbryos: Int,
    @ColumnInfo(name = "dead_hatchlings_nest") val deadHatchlingsNest: Int,
    @ColumnInfo(name = "live_hatchlings_nest") val liveHatchlingsNest: Int,
    @ColumnInfo(name = "comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Converts a NestExcavationModel object to a NestExcavationEntity.
 *
 * @param morningSurveyId The ID of the morning survey associated with this excavation.
 * @return A new NestExcavationEntity object containing the data from the NestExcavationModel.
 */
fun NestExcavationModel.asEntity(morningSurveyId: Long): NestExcavationEntity {
    return NestExcavationEntity(
        morningSurveyId = morningSurveyId,
        nestCode = nestCode,
        hatchedEggs = hatchedEggs,
        pippedDeadHatchlings = pippedDeadHatchlings,
        pippedLiveHatchlings = pippedLiveHatchlings,
        noEmbryos = noEmbryos,
        deadEmbryos = deadEmbryos,
        liveEmbryos = liveEmbryos,
        deadHatchlingsNest = deadHatchlingsNest,
        liveHatchlingsNest = liveHatchlingsNest,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Converts a NestExcavationEntity object to a NestExcavationModel.
 *
 * @return A new NestExcavationModel object containing the data from the NestExcavationEntity.
 */
fun NestExcavationEntity.asModel(): NestExcavationModel {
    return NestExcavationModel(
        nestCode = nestCode,
        hatchedEggs = hatchedEggs,
        pippedDeadHatchlings = pippedDeadHatchlings,
        pippedLiveHatchlings = pippedLiveHatchlings,
        noEmbryos = noEmbryos,
        deadEmbryos = deadEmbryos,
        liveEmbryos = liveEmbryos,
        deadHatchlingsNest = deadHatchlingsNest,
        liveHatchlingsNest = liveHatchlingsNest,
        commentsOrRemarks = commentsOrRemarks
    )
}