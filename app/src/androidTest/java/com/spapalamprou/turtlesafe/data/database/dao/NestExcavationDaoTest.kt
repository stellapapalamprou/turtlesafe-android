package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.entities.NestExcavationEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NestExcavationDaoTest {
    private lateinit var database: TurtleSafeDatabase
    private lateinit var dao: NestExcavationDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.nestExcavationDao()
    }

    @After
    fun shutDown() {
        database.close()
    }

    @Test
    fun insertNestExcavation() {
        runTest {
            val nestExcavationEntity = NestExcavationEntity(
                morningSurveyId = 123,
                nestCode = "N1234",
                hatchedEggs = 50,
                pippedDeadHatchlings = 5,
                pippedLiveHatchlings = 10,
                noEmbryos = 60,
                deadEmbryos = 8,
                liveEmbryos = 52,
                deadHatchlingsNest = 3,
                liveHatchlingsNest = 47,
                commentsOrRemarks = "Nest had a high rate of successful hatching."
            )
            val actual = dao.insert(nestExcavationEntity)

            val expected = NestExcavationEntity(
                id = 1,
                morningSurveyId = 123,
                nestCode = "N1234",
                hatchedEggs = 50,
                pippedDeadHatchlings = 5,
                pippedLiveHatchlings = 10,
                noEmbryos = 60,
                deadEmbryos = 8,
                liveEmbryos = 52,
                deadHatchlingsNest = 3,
                liveHatchlingsNest = 47,
                commentsOrRemarks = "Nest had a high rate of successful hatching."
            )
            assertEquals(expected.id, actual)
        }
    }
}