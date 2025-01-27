package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.entities.HatchingDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestHatchingEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestHatchingDaoTest {
    private lateinit var database: TurtleSafeDatabase
    private lateinit var dao: NestHatchingDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.nestHatchingDao()
    }

    @After
    fun shutDown() {
        database.close()
    }

    @Test
    fun insertNestHatching() {
        runTest {
            val nestHatchingEntity = NestHatchingEntity(
                morningSurveyId = 123,
                nestCode = "N1234",
                firstDayHatching = "2024-08-15",
                lastDayHatching = "2024-08-20",
                commentsOrRemarks = "Hatching observed over several days."
            )
            val actual = dao.insert(nestHatchingEntity)
            val expected = 1L
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertNestHatchingData() {
        runTest {
            val hatchingDataEntity = HatchingDataEntity(
                nestHatchingId = 1,
                selectedHatchingEvent = "Predation Attempt"
            )
            val actual = dao.insert(hatchingDataEntity)
            val expected = 1L
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertNestHatchingWithEvents() {
        runTest {
            val nestHatchingEntity = NestHatchingEntity(
                morningSurveyId = 123,
                nestCode = "N1234",
                firstDayHatching = "2024-08-15",
                lastDayHatching = "2024-08-20",
                commentsOrRemarks = "Hatching observed over several days."
            )
            val hatchingDataEntity = HatchingDataEntity(
                nestHatchingId = 1,
                selectedHatchingEvent = "Predation Attempt"
            )

            val actual = dao.insert(nestHatchingEntity, listOf(hatchingDataEntity))
            val expected = 1L
            assertEquals(expected, actual)
        }
    }
}