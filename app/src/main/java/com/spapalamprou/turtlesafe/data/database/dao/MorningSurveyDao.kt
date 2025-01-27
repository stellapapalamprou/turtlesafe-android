package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.spapalamprou.turtlesafe.data.database.entities.MorningSurveyEntity
import com.spapalamprou.turtlesafe.data.database.entities.MorningSurveyWithActivities

/**
 * Data Access Object (DAO) for managing morning survey data in the local database.
 *
 */
@Dao
interface MorningSurveyDao {
    /**
     * Inserts a MorningSurveyEntity into the database.
     *
     * @param morningSurveyEntity The morning survey entity to be inserted.
     * @return The ID of the newly inserted morning survey entity.
     */
    @Insert
    suspend fun insert(morningSurveyEntity: MorningSurveyEntity): Long

    /**
     * Retrieves a morning survey along with its associated activities by the survey's ID.
     *
     * @param id The ID of the morning survey to retrieve.
     * @return A list of MorningSurveyWithActivities containing the survey and its associated activities.
     */
    @Transaction
    @Query("SELECT * FROM MorningSurveyEntity WHERE id=:id")
    suspend fun getMorningSurvey(id: Long): List<MorningSurveyWithActivities>
}