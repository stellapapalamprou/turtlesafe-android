package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.entities.IncubationDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NestIncubationEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestIncubationDaoTest {
    private lateinit var database: TurtleSafeDatabase
    private lateinit var dao: NestIncubationDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.nestIncubationDao()
    }

    @After
    fun shutDown() {
        database.close()
    }

    @Test
    fun insertNestIncubation() {
        runTest {
            val nestIncubationEntity = NestIncubationEntity(
                morningSurveyId = 123,
                nestCode = "N1234",
                nestLocation = "Hatchery",
                hatcheryCode = "HATCH001",
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                commentsOrRemarks = "Nest monitored daily for activity."
            )
            val actual = dao.insert(nestIncubationEntity)
            val expected = 1L
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertNestIncubationData() {
        runTest {
            val incubationDataEntity = IncubationDataEntity(
                nestIncubationId = 1,
                selectedIncubationEvent = "Predation Attempt"
            )
            val actual = dao.insert(incubationDataEntity)
            val expected = 1L
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertNestIncubationWithEvents() {
        runTest {
            val nestIncubationEntity = NestIncubationEntity(
                morningSurveyId = 123,
                nestCode = "N1234",
                nestLocation = "Hatchery",
                hatcheryCode = "HATCH001",
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                commentsOrRemarks = "Nest monitored daily for activity."
            )
            val incubationDataEntity = IncubationDataEntity(
                nestIncubationId = 1,
                selectedIncubationEvent = "Predation Attempt"
            )

            val actual = dao.insert(nestIncubationEntity, listOf(incubationDataEntity))
            val expected = 1L
            assertEquals(expected, actual)
        }
    }
}