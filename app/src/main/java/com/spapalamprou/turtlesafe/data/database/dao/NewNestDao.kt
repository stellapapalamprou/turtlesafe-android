package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.spapalamprou.turtlesafe.data.database.entities.NewNestEntity

/**
 * Data Access Object (DAO) interface for managing new nest data in the local database.
 */
@Dao
interface NewNestDao {

    /**
     * Inserts a new NewNestEntity into the local database.
     *
     * @param newNestEntity The entity to be inserted.
     * @return The ID of the newly inserted new nest entity.
     */
    @Insert
    suspend fun insert(newNestEntity: NewNestEntity): Long
}