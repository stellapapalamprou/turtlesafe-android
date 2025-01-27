package com.spapalamprou.turtlesafe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spapalamprou.turtlesafe.data.database.dao.MorningSurveyDao
import com.spapalamprou.turtlesafe.data.database.dao.NestExcavationDao
import com.spapalamprou.turtlesafe.data.database.dao.NestHatchingDao
import com.spapalamprou.turtlesafe.data.database.dao.NestIncubationDao
import com.spapalamprou.turtlesafe.data.database.dao.NestRelocationDao
import com.spapalamprou.turtlesafe.data.database.dao.NewNestDao
import com.spapalamprou.turtlesafe.data.database.dao.NightSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.HatchingDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.IncubationDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.MorningSurveyEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestExcavationEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestHatchingEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestIncubationEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestRelocationEntity
import com.spapalamprou.turtlesafe.data.database.entities.NewNestEntity
import com.spapalamprou.turtlesafe.data.database.entities.NewTagDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NightSurveyEntity
import com.spapalamprou.turtlesafe.data.database.entities.OldTagDataEntity

/**
 * The main database class for the TurtleSafe application that includes the entities related to morning and night
 * surveys and the nesting activities.
 */
@Database(
    entities = [
        NestRelocationEntity::class,
        NestExcavationEntity::class,
        NewNestEntity::class,
        MorningSurveyEntity::class,
        NestIncubationEntity::class,
        IncubationDataEntity::class,
        NightSurveyEntity::class,
        OldTagDataEntity::class,
        NewTagDataEntity::class,
        NestHatchingEntity::class,
        HatchingDataEntity::class
    ], version = 1
)
abstract class TurtleSafeDatabase : RoomDatabase() {
    abstract fun nestRelocationDao(): NestRelocationDao
    abstract fun nestExcavationDao(): NestExcavationDao
    abstract fun newNestDao(): NewNestDao
    abstract fun morningSurveyDao(): MorningSurveyDao
    abstract fun nestIncubationDao(): NestIncubationDao
    abstract fun nightSurveyDao(): NightSurveyDao
    abstract fun nestHatchingDao(): NestHatchingDao
}