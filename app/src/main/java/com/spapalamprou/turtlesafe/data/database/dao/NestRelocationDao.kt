package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.spapalamprou.turtlesafe.data.database.entities.NestRelocationEntity

/**
 * Data Access Object (DAO) interface for managing nest relocation data in the local database.
 */
@Dao
interface NestRelocationDao {

    /**
     * Inserts a new NestRelocationEntity into the local database.
     *
     * @param nestRelocationEntity The entity to be inserted.
     * @return The ID of the newly inserted nest relocation entity.
     */
    @Insert
    suspend fun insert(nestRelocationEntity: NestRelocationEntity): Long
}