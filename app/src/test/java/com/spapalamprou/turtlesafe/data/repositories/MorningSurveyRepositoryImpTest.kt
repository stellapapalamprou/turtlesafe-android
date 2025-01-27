package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.api.MorningSurveyApi
import com.spapalamprou.turtlesafe.data.api.MorningSurveyJsonResponse
import com.spapalamprou.turtlesafe.data.api.asJson
import com.spapalamprou.turtlesafe.data.database.dao.MorningSurveyDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.data.workers.MorningSurveyWorkerManager
import com.spapalamprou.turtlesafe.domain.models.MorningSurveyModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MorningSurveyRepositoryImpTest {
    private lateinit var repository: MorningSurveyRepositoryImp
    private val dao: MorningSurveyDao = mockk()
    private val api: MorningSurveyApi = mockk()
    private val manager: MorningSurveyWorkerManager = mockk()

    @Before
    fun setUp() {
        repository = MorningSurveyRepositoryImp(dao, api, manager)
    }

    @Test
    fun morningSurvey_savedToDatabase() {
        runTest {
            val morningSurvey = MorningSurveyModel(
                date = "2024-08-14",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                newNestList = listOf(),
                nestIncubationList = listOf(),
                nestRelocationList = listOf(),
                nestHatchingList = listOf(),
                nestExcavationList = listOf()
            )

            coEvery { dao.insert(morningSurvey.asEntity()) } returns 123L
            val actual = repository.saveToDatabase(morningSurvey)
            coVerify { dao.insert(morningSurvey.asEntity()) }
            assertEquals(123L, actual)
        }
    }

    @Test
    fun morningSurvey_sentToServer() {
        runTest {
            val morningSurvey = MorningSurveyModel(
                date = "14/08/2024",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings.",
                newNestList = listOf(),
                nestIncubationList = listOf(),
                nestRelocationList = listOf(),
                nestHatchingList = listOf(),
                nestExcavationList = listOf()
            )

            val morningSurveyResponse = MorningSurveyJsonResponse(
                id = 1,
                date = "2024-08-14",
                time = "07:30:00",
                area = "LAK",
                beach = "Selinitsa",
                nestingAttemptSwitch = true,
                numberOfAttempts = 2,
                commentsOrRemarks = "Survey conducted smoothly with some unusual findings."
            )

            coEvery { api.createMorningSurvey(morningSurvey.asJson()) } returns morningSurveyResponse
            repository.sendToServer(morningSurvey)
            coVerify { api.createMorningSurvey(morningSurvey.asJson()) }
        }
    }

    @Test
    fun morningSurvey_testWorker() {
        runTest {
            val id = 123L

            coEvery { manager.startWorker(id) } returns Unit
            repository.startWorker(id)
            coVerify { manager.startWorker(id) }
        }
    }
}