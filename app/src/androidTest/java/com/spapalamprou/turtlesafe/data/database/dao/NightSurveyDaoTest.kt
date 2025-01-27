package com.spapalamprou.turtlesafe.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.entities.NewTagDataEntity
import com.spapalamprou.turtlesafe.data.database.entities.NightSurveyEntity
import com.spapalamprou.turtlesafe.data.database.entities.NightSurveyWithTags
import com.spapalamprou.turtlesafe.data.database.entities.OldTagDataEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NightSurveyDaoTest {
    private lateinit var database: TurtleSafeDatabase
    private lateinit var dao: NightSurveyDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.nightSurveyDao()
    }

    @After
    fun shutDown() {
        database.close()
    }

    @Test
    fun insertNightSurvey() {
        runTest {
            val nightSurveyEntity = NightSurveyEntity(
                date = "2023-09-12",
                area = "Some Area",
                beach = "Some Beach",
                sector = "Some Sector",
                subsector = "Some Subsector",
                tagger = "Tagger Name",
                lostTagScars = "Some description",
                straightCarapaceLength = 82.5,
                straightCarapaceWidth = 65.2,
                curvedCarapaceLength = 85.3,
                curvedCarapaceWidth = 67.0,
                damageFlippersHead = "None",
                damageCarapace = "None",
                nestEmergenceSwitch = true,
                nestCode = "N1234",
                depthTopEgg = 30.5,
                depthBottomNests = 55.0,
                distanceToSea = 10.0,
                numEggsRelocated = 100,
                relocationComments = "Relocated due to high tide",
                numEggsExcavated = 85,
                startLaying = "22:00:00",
                startCover = "22:30:00",
                startCamouflage = "22:45:00",
                departNestSite = "23:00:00",
                turtleAtSea = "23:15:00",
                commentsRemarks = "Turtle appeared healthy."
            )

            val oldTagDataEntity = OldTagDataEntity(
                nightSurveyId = 1,
                selectedOldTagLocation = "left_flipper",
                oldTagCode = "A12345",
                isTurtleSafeSwitchChecked = true,
                tagNotes = "Tag is slightly worn but legible."
            )

            val newTagDataEntity = NewTagDataEntity(
                nightSurveyId = 1,
                selectedNewTagLocation = "right_flipper",
                newTagCode = "D98765",
                isTagSwitchChecked = false,
                selectedNewScarType = "fresh_scar",
                newScarLocation = "neck"
            )

            dao.insert(
                nightSurveyEntity,
                listOf(oldTagDataEntity),
                listOf(newTagDataEntity)
            )

            val actual = dao.getNightSurvey(id = 1)
            val expected = NightSurveyWithTags(
                NightSurveyEntity(
                    id = 1,
                    date = "2023-09-12",
                    area = "Some Area",
                    beach = "Some Beach",
                    sector = "Some Sector",
                    subsector = "Some Subsector",
                    tagger = "Tagger Name",
                    lostTagScars = "Some description",
                    straightCarapaceLength = 82.5,
                    straightCarapaceWidth = 65.2,
                    curvedCarapaceLength = 85.3,
                    curvedCarapaceWidth = 67.0,
                    damageFlippersHead = "None",
                    damageCarapace = "None",
                    nestEmergenceSwitch = true,
                    nestCode = "N1234",
                    depthTopEgg = 30.5,
                    depthBottomNests = 55.0,
                    distanceToSea = 10.0,
                    numEggsRelocated = 100,
                    relocationComments = "Relocated due to high tide",
                    numEggsExcavated = 85,
                    startLaying = "22:00:00",
                    startCover = "22:30:00",
                    startCamouflage = "22:45:00",
                    departNestSite = "23:00:00",
                    turtleAtSea = "23:15:00",
                    commentsRemarks = "Turtle appeared healthy."
                ),
                listOf(
                    OldTagDataEntity(
                        id = 1,
                        nightSurveyId = 1,
                        selectedOldTagLocation = "left_flipper",
                        oldTagCode = "A12345",
                        isTurtleSafeSwitchChecked = true,
                        tagNotes = "Tag is slightly worn but legible."
                    )
                ),
                listOf(
                    NewTagDataEntity(
                        id = 1,
                        nightSurveyId = 1,
                        selectedNewTagLocation = "right_flipper",
                        newTagCode = "D98765",
                        isTagSwitchChecked = false,
                        selectedNewScarType = "fresh_scar",
                        newScarLocation = "neck"
                    )
                )
            )
            assertEquals(expected, actual.first())
        }
    }
}