package com.spapalamprou.turtlesafe.data.repositories

import com.spapalamprou.turtlesafe.data.database.dao.NestIncubationDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.IncubationDataModel
import com.spapalamprou.turtlesafe.domain.models.NestIncubationModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NestIncubationRepositoryImpTest {
    private lateinit var repository: NestIncubationRepositoryImp
    private val dao: NestIncubationDao = mockk()

    @Before
    fun setUp() {
        repository = NestIncubationRepositoryImp(dao)
    }

    @Test
    fun nestIncubation_savedToDatabase() {
        runTest {
            val incubationEvent = IncubationDataModel(
                selectedIncubationEvent = "Predation"
            )

            val incubation = NestIncubationModel(
                nestCode = "123",
                nestLocation = "Hatchery",
                hatcheryCode = "H456",
                incubationDataList = listOf(incubationEvent),
                protectedNestSwitch = true,
                protectionMeasures = "Tape",
                commentsOrRemarks = "Normal incubation"
            )

            val morningSurveyId = 1010L

            coEvery {
                dao.insert(nestIncubationEntity = incubation.asEntity(morningSurveyId),
                    incubationDataEntities = incubation.incubationDataList.map { entry -> entry.asEntity() })
            } returns 1L

            repository.saveToDatabase(incubation, morningSurveyId)
            coVerify {
                dao.insert(nestIncubationEntity = incubation.asEntity(morningSurveyId),
                    incubationDataEntities = incubation.incubationDataList.map { entry -> entry.asEntity() })
            }
        }
    }

}