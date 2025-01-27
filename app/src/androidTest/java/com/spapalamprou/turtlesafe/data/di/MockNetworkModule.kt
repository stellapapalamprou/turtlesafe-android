package com.spapalamprou.turtlesafe.data.di

import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.MorningSurveyApi
import com.spapalamprou.turtlesafe.data.api.NewNestApi
import com.spapalamprou.turtlesafe.data.api.NightSurveyApi
import com.spapalamprou.turtlesafe.data.api.RefreshTokenApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient
            .Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit
            .Builder()
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .serializeNulls()
                        .create()
                )
            )
            .baseUrl("http://localhost:12345/")
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideNewNestApi(retrofit: Retrofit): NewNestApi {
        return retrofit.create(NewNestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNightSurveyApi(retrofit: Retrofit): NightSurveyApi {
        return retrofit.create(NightSurveyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMorningSurveyApi(retrofit: Retrofit): MorningSurveyApi {
        return retrofit.create(MorningSurveyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi {
        return retrofit.create(AuthenticationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenApi(): RefreshTokenApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .serializeNulls()
                        .create()
                )
            )
            .baseUrl("http://localhost:12345/")
            .build()
        return retrofit.create(RefreshTokenApi::class.java)
    }
}