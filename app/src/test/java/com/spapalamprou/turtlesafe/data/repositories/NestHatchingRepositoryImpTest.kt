package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestHatchingDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.HatchingDataModel
import com.spapalamprou.turtlesafe.domain.models.NestHatchingModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NestHatchingRepositoryImpTest {
    private lateinit var repository: NestHatchingRepositoryImp
    private val dao: NestHatchingDao = mockk()

    @Before
    fun setUp() {
        repository = NestHatchingRepositoryImp(dao)
    }

    @Test
    fun nestHatching_savedToDatabase() {
        runTest {
            val hatchingEvent = HatchingDataModel(
                selectedHatchingEvent = "Inundation"
            )

            val hatching = NestHatchingModel(
                nestCode = "123",
                firstDayHatching = "2024-09-01",
                lastDayHatching = "2024-09-03",
                hatchingDataList = listOf(hatchingEvent),
                commentsOrRemarks = "Nest observed in good condition."
            )

            val morningSurveyId = 1010L

            coEvery {
                dao.insert(nestHatchingEntity = hatching.asEntity(morningSurveyId),
                    hatchingDataEntities = hatching.hatchingDataList.map { entry -> entry.asEntity() })
            } returns 1L

            repository.saveToDatabase(hatching, morningSurveyId)
            coVerify {
                dao.insert(nestHatchingEntity = hatching.asEntity(morningSurveyId),
                    hatchingDataEntities = hatching.hatchingDataList.map { entry -> entry.asEntity() })
            }
        }
    }
}