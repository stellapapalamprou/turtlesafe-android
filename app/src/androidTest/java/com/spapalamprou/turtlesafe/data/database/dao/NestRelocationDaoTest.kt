package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.entities.NestRelocationEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestRelocationDaoTest {
    private lateinit var database: TurtleSafeDatabase
    private lateinit var dao: NestRelocationDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.nestRelocationDao()
    }

    @After
    fun shutDown() {
        database.close()
    }

    @Test
    fun insertNestRelocation() {
        runTest {
            val nestRelocationEntity = NestRelocationEntity(
                morningSurveyId = 123,
                oldNestCode = "NC001",
                relocatedTo = "BoB",
                sector = "A",
                subsector = "A2",
                reasonForRelocation = "Inundation",
                newNestCode = "NC002",
                depthTopEgg = 25.5,
                depthBottomNest = 30.0,
                distanceToSea = 100.0,
                numOfEggsTransplanted = 80,
                commentsOrRemarks = "Relocated successfully"
            )
            val actual = dao.insert(nestRelocationEntity)
            val expected = 1L
            assertEquals(expected, actual)
        }
    }
}