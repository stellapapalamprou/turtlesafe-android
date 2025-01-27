package com.spapalamprou.turtlesafe.data.di

import android.content.Context
import androidx.room.Room
import com.spapalamprou.turtlesafe.data.database.TurtleSafeDatabase
import com.spapalamprou.turtlesafe.data.database.dao.MorningSurveyDao
import com.spapalamprou.turtlesafe.data.database.dao.NestExcavationDao
import com.spapalamprou.turtlesafe.data.database.dao.NestHatchingDao
import com.spapalamprou.turtlesafe.data.database.dao.NestIncubationDao
import com.spapalamprou.turtlesafe.data.database.dao.NestRelocationDao
import com.spapalamprou.turtlesafe.data.database.dao.NewNestDao
import com.spapalamprou.turtlesafe.data.database.dao.NightSurveyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class MockDatabaseModule {

    @Singleton
    @Provides
    fun provideTurtleSafeDatabase(@ApplicationContext context: Context): TurtleSafeDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            TurtleSafeDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideNestRelocationDao(database: TurtleSafeDatabase): NestRelocationDao {
        return database.nestRelocationDao()
    }

    @Singleton
    @Provides
    fun provideNestExcavationDao(database: TurtleSafeDatabase): NestExcavationDao {
        return database.nestExcavationDao()
    }

    @Singleton
    @Provides
    fun provideNewNestDao(database: TurtleSafeDatabase): NewNestDao {
        return database.newNestDao()
    }

    @Singleton
    @Provides
    fun provideMorningSurveyDao(database: TurtleSafeDatabase): MorningSurveyDao {
        return database.morningSurveyDao()
    }

    @Singleton
    @Provides
    fun provideNestIncubationDao(database: TurtleSafeDatabase): NestIncubationDao {
        return database.nestIncubationDao()
    }

    @Singleton
    @Provides
    fun provideNightSurveyDao(database: TurtleSafeDatabase): NightSurveyDao {
        return database.nightSurveyDao()
    }

    @Singleton
    @Provides
    fun provideNestHatchingDao(database: TurtleSafeDatabase): NestHatchingDao {
        return database.nestHatchingDao()
    }
}