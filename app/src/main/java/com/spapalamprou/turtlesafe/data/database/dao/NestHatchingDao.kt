package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.spapalamprou.turtlesafe.data.database.entities.HatchingDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestHatchingEntity

/**
 * Data Access Object (DAO) interface for managing nest hatching data in the local database.
 */
@Dao
interface NestHatchingDao {
    /**
     * Inserts a new NestHatchingEntity into the local database.
     *
     * @param nestHatchingEntity The entity to be inserted.
     * @return The ID of the newly inserted nest hatching entity.
     */
    @Insert
    suspend fun insert(nestHatchingEntity: NestHatchingEntity): Long

    /**
     * Inserts a new HatchingDataEntity into the database.
     *
     * @param hatchingDataEntity The entity representing hatching data to be inserted.
     * @return The ID of the newly inserted hatching data entity.
     */
    @Insert
    suspend fun insert(hatchingDataEntity: HatchingDataEntity): Long

    /**
     * Inserts a NestHatchingEntity along with its associated list of hatching data entities into the local database.
     *
     * @param nestHatchingEntity The nest hatching entity representing the nest hatching details.
     * @param hatchingDataEntities A list of hatching data entity objects that correspond to the hatching events of the nest.
     * @return The ID of the newly inserted NestHatchingEntity with its associated events.
     */
    @Transaction
    suspend fun insert(
        nestHatchingEntity: NestHatchingEntity,
        hatchingDataEntities: List<HatchingDataEntity>
    ): Long {
        val nestHatchingEntryId = insert(nestHatchingEntity = nestHatchingEntity)

        for (event in hatchingDataEntities) {
            event.nestHatchingId = nestHatchingEntryId
            insert(hatchingDataEntity = event)
        }
        return nestHatchingEntryId
    }
}