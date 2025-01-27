package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestRelocationDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NestRelocationModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NestRelocationRepositoryImpTest {
    private lateinit var repository: NestRelocationRepositoryImp
    private val dao: NestRelocationDao = mockk()

    @Before
    fun setUp() {
        repository = NestRelocationRepositoryImp(dao)
    }

    @Test
    fun nestRelocation_saveToDatabase() {
        runTest {
            val relocation = NestRelocationModel(
                oldNestCode = "N001",
                relocatedTo = "BoB",
                sector = "A",
                subsector = "A2",
                reasonForRelocation = "Inundation",
                newNestCode = "N002",
                depthTopEgg = 10.5,
                depthBottomNest = 35.7,
                distanceToSea = 50.0,
                numOfEggsTransplanted = 120,
                commentsOrRemarks = "Relocation successful"
            )

            val morningSurveyId = 1010L

            coEvery {
                dao.insert(relocation.asEntity(morningSurveyId))
            } returns 1L

            repository.saveToDatabase(relocation, morningSurveyId)
            coVerify { dao.insert(relocation.asEntity(morningSurveyId)) }
        }
    }
}