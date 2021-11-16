package com.parhambaghebani.di

import android.content.Context
import com.parhambaghebani.utility.ApiManager
import com.parhambaghebani.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiManager(@ApplicationContext context: Context, okHttpClient: OkHttpClient, jsonParser: Json): ApiManager {
        return ApiManager(okHttpClient, jsonParser)
    }
}