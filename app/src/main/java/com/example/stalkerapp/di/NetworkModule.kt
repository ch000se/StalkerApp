package com.example.stalkerapp.di

import com.example.stalkerapp.data.local.StalkerDatabase
import com.example.stalkerapp.data.remote.StalkerApi
import com.example.stalkerapp.data.repository.RemoteDataSourceImpl
import com.example.stalkerapp.domain.repository.RemoteDataSource
import com.example.stalkerapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideStalkerApi(retrofit: Retrofit): StalkerApi {
        return retrofit.create(StalkerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(stalkerApi: StalkerApi , stalkerDatabase: StalkerDatabase): RemoteDataSource {
        return RemoteDataSourceImpl(stalkerApi, stalkerDatabase)
    }
}
