package com.spapalamprou.turtlesafe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spapalamprou.turtlesafe.domain.models.NewNestModel

/**
 * Entity representing a new nest record in the database.
 *
 * @property id The unique identifier for the new nest record, automatically generated.
 * @property morningSurveyId The ID of the associated morning survey, linking this new nest to a specific survey.
 * @property layingDate The date when the nest was laid.
 * @property area The area where the nest is located.
 * @property beach The beach where the nest is found.
 * @property sector The sector where the nest was found.
 * @property subsector The subsector where the nest was found.
 * @property nestCode The unique code assigned to the nest.
 * @property trackType The type of turtle track associated with the nest.
 * @property gpsLatitude The GPS latitude coordinate of the nest location.
 * @property gpsLongtitude The GPS longtitude coordinate of the nest location.
 * @property photoUri The URI pointing to a photo of the nest.
 * @property protectedNestSwitch A boolean indicating whether the nest is under protection.
 * @property protectionMeasures A description of the measures taken to protect the nest.
 * @property turtleTags Tags found on the turtle that laid the eggs.
 * @property emergenceOrEvent A description of any emergence events or related occurrences.
 * @property depthTopEgg The depth of the top egg in the nest.
 * @property distanceToSea The distance from the nest to the sea.
 * @property commentsOrRemarks Any additional comments or remarks regarding the new nest.
 */
@Entity
data class NewNestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "morning_survey_id") val morningSurveyId: Long,
    @ColumnInfo(name = "laying_date") val layingDate: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "beach") val beach: String,
    @ColumnInfo(name = "sector") val sector: String,
    @ColumnInfo(name = "subsector") val subsector: String,
    @ColumnInfo(name = "nest_code") val nestCode: String,
    @ColumnInfo(name = "track_type") val trackType: String,
    @ColumnInfo(name = "gps_latitude") val gpsLatitude: Double,
    @ColumnInfo(name = "gps_longtitude") val gpsLongtitude: Double,
    @ColumnInfo(name = "photo_uri") val photoUri: String?,
    @ColumnInfo(name = "protected_nest_switch") val protectedNestSwitch: Boolean,
    @ColumnInfo(name = "protection_measures") val protectionMeasures: String,
    @ColumnInfo(name = "turtle_tags") val turtleTags: String,
    @ColumnInfo(name = "emergence_or_event") val emergenceOrEvent: String,
    @ColumnInfo(name = "depth_top_egg") val depthTopEgg: Double,
    @ColumnInfo(name = "distance_to_sea") val distanceToSea: Double,
    @ColumnInfo(name = "comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Converts a NewNestModel object to a NewNestEntity.
 *
 * @param morningSurveyId The ID of the morning survey associated with this new nest.
 * @return A new NewNestEntity object containing data from the NewNestModel.
 */
fun NewNestModel.asEntity(morningSurveyId: Long): NewNestEntity {
    return NewNestEntity(
        morningSurveyId = morningSurveyId,
        layingDate = layingDate,
        area = area,
        beach = beach,
        sector = sector,
        subsector = subsector,
        nestCode = nestCode,
        trackType = trackType,
        gpsLatitude = gpsLatitude,
        gpsLongtitude = gpsLongtitude,
        photoUri = photoUri,
        protectedNestSwitch = protectedNestSwitch,
        protectionMeasures = protectionMeasures,
        turtleTags = turtleTags,
        emergenceOrEvent = emergenceOrEvent,
        depthTopEgg = depthTopEgg,
        distanceToSea = distanceToSea,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Converts a NewNestEntity object to a NewNestModel.
 *
 * @return A new NewNestModel object containing data from the NewNestEntity.
 */
fun NewNestEntity.asModel(): NewNestModel {
    return NewNestModel(
        layingDate = layingDate,
        area = area,
        beach = beach,
        sector = sector,
        subsector = subsector,
        nestCode = nestCode,
        trackType = trackType,
        gpsLatitude = gpsLatitude,
        gpsLongtitude = gpsLongtitude,
        photoUri = photoUri,
        protectedNestSwitch = protectedNestSwitch,
        protectionMeasures = protectionMeasures,
        turtleTags = turtleTags,
        emergenceOrEvent = emergenceOrEvent,
        depthTopEgg = depthTopEgg,
        distanceToSea = distanceToSea,
        commentsOrRemarks = commentsOrRemarks
    )
}