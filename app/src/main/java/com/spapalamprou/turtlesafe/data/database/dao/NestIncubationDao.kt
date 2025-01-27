package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.spapalamprou.turtlesafe.data.database.entities.IncubationDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestIncubationEntity

/**
 * Data Access Object (DAO) interface for managing nest incubation data in the local database.
 */
@Dao
interface NestIncubationDao {

    /**
     * Inserts a new NestIncubationEntity into the local database.
     *
     * @param nestIncubationEntity The entity to be inserted.
     * @return The ID of the newly inserted nest incubation entity.
     */
    @Insert
    suspend fun insert(nestIncubationEntity: NestIncubationEntity): Long

    /**
     * Inserts a new IncubationDataEntity into the database.
     *
     * @param incubationDataEntity The entity representing incubation data to be inserted.
     * @return The ID of the newly inserted incubation data entity.
     */
    @Insert
    suspend fun insert(incubationDataEntity: IncubationDataEntity): Long

    /**
     * Inserts a NestIncubationEntity along with its associated list of incubation data entities into the local database.
     *
     * @param nestIncubationEntity The nest incubation entity representing the nest incubation details.
     * @param incubationDataEntities A list of incubation data entity objects that correspond to the incubation events of the nest.
     * @return The ID of the newly inserted NestIncubationEntity with its associated events.
     */
    @Transaction
    suspend fun insert(
        nestIncubationEntity: NestIncubationEntity,
        incubationDataEntities: List<IncubationDataEntity>
    ): Long {
        val nestIncubationEntryId = insert(nestIncubationEntity = nestIncubationEntity)

        for (event in incubationDataEntities) {
            event.nestIncubationId = nestIncubationEntryId
            insert(incubationDataEntity = event)
        }
        return nestIncubationEntryId
    }
}