package com.spapalamprou.turtlesafe.data.di

import android.content.Context
import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.MorningSurveyApi
import com.spapalamprou.turtlesafe.data.api.NewNestApi
import com.spapalamprou.turtlesafe.data.api.NightSurveyApi
import com.spapalamprou.turtlesafe.data.database.dao.MorningSurveyDao
import com.spapalamprou.turtlesafe.data.database.dao.NestExcavationDao
import com.spapalamprou.turtlesafe.data.database.dao.NestHatchingDao
import com.spapalamprou.turtlesafe.data.database.dao.NestIncubationDao
import com.spapalamprou.turtlesafe.data.database.dao.NestRelocationDao
import com.spapalamprou.turtlesafe.data.database.dao.NewNestDao
import com.spapalamprou.turtlesafe.data.database.dao.NightSurveyDao
import com.spapalamprou.turtlesafe.data.repositories.LoginRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.MorningSurveyRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.NestExcavationRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.NestHatchingRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.NestIncubationRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.NestRelocationRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.NewNestRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.NightSurveyRepositoryImp
import com.spapalamprou.turtlesafe.data.repositories.SignUpRepositoryImp
import com.spapalamprou.turtlesafe.data.storage.TokenStorage
import com.spapalamprou.turtlesafe.data.storage.TokenStorageImp
import com.spapalamprou.turtlesafe.data.workers.MorningSurveyWorkerManager
import com.spapalamprou.turtlesafe.data.workers.MorningSurveyWorkerManagerImp
import com.spapalamprou.turtlesafe.data.workers.NightSurveyWorkerManager
import com.spapalamprou.turtlesafe.data.workers.NightSurveyWorkerManagerImp
import com.spapalamprou.turtlesafe.domain.repositories.LoginRepository
import com.spapalamprou.turtlesafe.domain.repositories.MorningSurveyRepository
import com.spapalamprou.turtlesafe.domain.repositories.NestExcavationRepository
import com.spapalamprou.turtlesafe.domain.repositories.NestHatchingRepository
import com.spapalamprou.turtlesafe.domain.repositories.NestIncubationRepository
import com.spapalamprou.turtlesafe.domain.repositories.NestRelocationRepository
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import com.spapalamprou.turtlesafe.domain.repositories.NightSurveyRepository
import com.spapalamprou.turtlesafe.domain.repositories.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideNestRelocationRepositoryImp(
        nestRelocationDao: NestRelocationDao
    ): NestRelocationRepository {
        return NestRelocationRepositoryImp(nestRelocationDao)
    }

    @Singleton
    @Provides
    fun provideNestExcavationRepositoryImp(
        nestExcavationDao: NestExcavationDao
    ): NestExcavationRepository {
        return NestExcavationRepositoryImp(nestExcavationDao)
    }

    @Singleton
    @Provides
    fun provideNewNestRepositoryImp(
        @ApplicationContext context: Context,
        newNestDao: NewNestDao,
        newNestApi: NewNestApi
    ): NewNestRepository {
        return NewNestRepositoryImp(context, newNestDao, newNestApi)
    }

    @Singleton
    @Provides
    fun provideMorningSurveyRepositoryImp(
        morningSurveyDao: MorningSurveyDao,
        morningSurveyApi: MorningSurveyApi,
        morningSurveyWorkerManager: MorningSurveyWorkerManager
    ): MorningSurveyRepository {
        return MorningSurveyRepositoryImp(
            morningSurveyDao,
            morningSurveyApi,
            morningSurveyWorkerManager
        )
    }

    @Singleton
    @Provides
    fun provideNestIncubationRepositoryImp(
        nestIncubationDao: NestIncubationDao
    ): NestIncubationRepository {
        return NestIncubationRepositoryImp(nestIncubationDao)
    }

    @Singleton
    @Provides
    fun provideNightSurveyRepositoryImp(
        nightSurveyDao: NightSurveyDao,
        nightSurveyApi: NightSurveyApi,
        nightSurveyWorkerManager: NightSurveyWorkerManager
    ): NightSurveyRepository {
        return NightSurveyRepositoryImp(nightSurveyDao, nightSurveyApi, nightSurveyWorkerManager)
    }

    @Singleton
    @Provides
    fun provideLoginRepositoryImp(
        authenticationApi: AuthenticationApi,
        tokenStorage: TokenStorage
    ): LoginRepository {
        return LoginRepositoryImp(authenticationApi, tokenStorage)
    }

    @Singleton
    @Provides
    fun provideTokenStorageImp(@ApplicationContext context: Context): TokenStorage {
        return TokenStorageImp(context)
    }

    @Singleton
    @Provides
    fun provideSignUpRepositoryImp(
        authenticationApi: AuthenticationApi
    ): SignUpRepository {
        return SignUpRepositoryImp(authenticationApi)
    }

    @Singleton
    @Provides
    fun provideNestHatchingRepositoryImp(
        nestHatchingDao: NestHatchingDao
    ): NestHatchingRepository {
        return NestHatchingRepositoryImp(nestHatchingDao)
    }

    @Singleton
    @Provides
    fun provideNightSurveyWorkerManagerImp(
        @ApplicationContext context: Context
    ): NightSurveyWorkerManager {
        return NightSurveyWorkerManagerImp(context)
    }

    @Singleton
    @Provides
    fun provideMorningSurveyWorkerManagerImp(
        @ApplicationContext context: Context
    ): MorningSurveyWorkerManager {
        return MorningSurveyWorkerManagerImp(context)
    }
}