package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.entities.NewNestEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewNestDaoTest {
    private lateinit var database: TurtleSafeDatabase
    private lateinit var dao: NewNestDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.newNestDao()
    }

    @After
    fun shutDown() {
        database.close()
    }

    @Test
    fun insertNewNest() {
        runTest {
            val newNestEntity = NewNestEntity(
                morningSurveyId = 123,
                layingDate = "2024-09-25",
                area = "LAK",
                beach = "Selinitsa",
                sector = "A",
                subsector = "A2",
                nestCode = "NC001",
                trackType = "B",
                gpsLatitude = 36.7783,
                gpsLongtitude = -119.4179,
                photoUri = null,
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                turtleTags = "TT123, TT124",
                emergenceOrEvent = "Inundation",
                depthTopEgg = 20.0,
                distanceToSea = 50.0,
                commentsOrRemarks = "New nest is safe"

            )
            val actual = dao.insert(newNestEntity)
            val expected = 1L
            assertEquals(expected, actual)
        }
    }
}