package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.spapalamprou.turtlesafe.data.database.entities.NewTagDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NightSurveyEntity
import com.spapalamprou.turtlesafe.data.database.entities.NightSurveyWithTags
import com.spapalamprou.turtlesafe.data.database.entities.OldTagDataEntity

/**
 * Data Access Object (DAO) interface for managing night survey data in the local database.
 */
@Dao
interface NightSurveyDao {

    /**
     * Inserts a NightSurveyEntity into the database.
     *
     * @param nightSurveyEntity The night survey entity to be inserted.
     * @return The ID of the newly inserted night survey entity.
     */
    @Insert
    suspend fun insert(nightSurveyEntity: NightSurveyEntity): Long

    /**
     * Inserts an OldTagDataEntity into the database.
     *
     * @param oldTagDataEntity The old tag data entity to be inserted.
     * @return The ID of the newly inserted old tag data entity.
     */
    @Insert
    suspend fun insert(oldTagDataEntity: OldTagDataEntity)

    /**
     * Inserts an NewTagDataEntity into the database.
     *
     * @param newTagDataEntity The new tag data entity to be inserted.
     * @return The ID of the newly inserted new tag data entity.
     */
    @Insert
    suspend fun insert(newTagDataEntity: NewTagDataEntity)

    /**
     * Inserts a NightSurveyEntity along with its associated lists of old tag data and new tag data entities into the local database.
     *
     * @param nightSurveyEntity The night survey entity representing the night survey details.
     * @param oldTagEntities A list of old tag data entity objects that correspond to the old tags found on the turtle.
     * @param newTagEntities A list of new tag data entity objects that correspond to the new tags placed on the turtle.
     * @return The ID of the newly inserted NightSurveyEntity with its associated tags.
     */
    @Transaction
    suspend fun insert(
        nightSurveyEntity: NightSurveyEntity,
        oldTagEntities: List<OldTagDataEntity>,
        newTagEntities: List<NewTagDataEntity>
    ): Long {
        val nightSurveyEntryId = insert(nightSurveyEntity = nightSurveyEntity)

        for (tag in oldTagEntities) {
            tag.nightSurveyId = nightSurveyEntryId
            insert(oldTagDataEntity = tag)
        }

        for (tag in newTagEntities) {
            tag.nightSurveyId = nightSurveyEntryId
            insert(newTagDataEntity = tag)
        }
        return nightSurveyEntryId
    }

    /**
     * Retrieves a night survey along with its associated tags by the survey's ID.
     *
     * @param id The ID of the night survey to retrieve.
     * @return A list of NightSurveyWithTags containing the survey and its associated tags.
     */
    @Transaction
    @Query("SELECT * FROM NightSurveyEntity WHERE id=:id")
    suspend fun getNightSurvey(id: Long): List<NightSurveyWithTags>
}