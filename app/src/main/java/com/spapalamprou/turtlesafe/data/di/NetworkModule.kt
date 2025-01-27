package com.spapalamprou.turtlesafe.data.di

import com.spapalamprou.turtlesafe.data.api.AuthenticationApi
import com.spapalamprou.turtlesafe.data.api.MorningSurveyApi
import com.spapalamprou.turtlesafe.data.api.NewNestApi
import com.spapalamprou.turtlesafe.data.api.NightSurveyApi
import com.spapalamprou.turtlesafe.data.api.RefreshTokenApi
import com.spapalamprou.turtlesafe.data.authenticator.RequestAuthenticator
import com.spapalamprou.turtlesafe.data.interceptors.AccessTokenInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        requestAuthenticator: RequestAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient
            .Builder()
            .authenticator(requestAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor)
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
            .baseUrl("http://10.0.2.2:8000/")
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
            .baseUrl("http://10.0.2.2:8000/")
            .build()
        return retrofit.create(RefreshTokenApi::class.java)
    }
}