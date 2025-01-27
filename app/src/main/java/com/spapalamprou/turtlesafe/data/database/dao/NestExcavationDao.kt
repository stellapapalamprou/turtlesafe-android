package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.spapalamprou.turtlesafe.data.database.entities.NestExcavationEntity

/**
 * Data Access Object (DAO) interface for managing nest excavation data in the local database.
 */
@Dao
interface NestExcavationDao {
    /**
     * Inserts a new NestExcavationEntity into the local database.
     *
     * @param nestExcavationEntity The entity to be inserted.
     * @return The ID of the newly inserted nest excavation entity.
     */
    @Insert
    suspend fun insert(nestExcavationEntity: NestExcavationEntity): Long
}