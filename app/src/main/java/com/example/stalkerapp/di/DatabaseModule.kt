package com.example.stalkerapp.di

import android.content.Context
import androidx.room.Room
import com.example.stalkerapp.data.local.StalkerDatabase
import com.example.stalkerapp.data.repository.LocalDataSourceImpl
import com.example.stalkerapp.domain.repository.LocalDataSource
import com.example.stalkerapp.util.Constants.STALKER_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StalkerDatabase {
        return Room.databaseBuilder(
            context,
            StalkerDatabase::class.java,
            STALKER_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(stalkerDatabase: StalkerDatabase): LocalDataSource{
        return LocalDataSourceImpl(stalkerDatabase)
    }
}
