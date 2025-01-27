package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestExcavationDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NestExcavationModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NestExcavationRepositoryImpTest {
    private lateinit var repository: NestExcavationRepositoryImp
    private val dao: NestExcavationDao = mockk()

    @Before
    fun setUp() {
        repository = NestExcavationRepositoryImp(dao)
    }

    @Test
    fun nestExcavation_savedToDatabase() {
        runTest {
            val excavation = NestExcavationModel(
                nestCode = "123",
                hatchedEggs = 50,
                pippedDeadHatchlings = 2,
                pippedLiveHatchlings = 5,
                noEmbryos = 3,
                deadEmbryos = 4,
                liveEmbryos = 6,
                deadHatchlingsNest = 1,
                liveHatchlingsNest = 8,
                commentsOrRemarks = "Nest observed in good condition."
            )

            val morningSurveyId = 1010L

            coEvery {
                dao.insert(excavation.asEntity(morningSurveyId))
            } returns 1L

            repository.saveToDatabase(excavation, morningSurveyId)
            coVerify { dao.insert(excavation.asEntity(morningSurveyId)) }
        }
    }
}