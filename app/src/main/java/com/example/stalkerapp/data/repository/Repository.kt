package com.example.stalkerapp.data.repository

import androidx.paging.PagingData
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.domain.repository.DataStoreOperations
import com.example.stalkerapp.domain.repository.LocalDataSource
import com.example.stalkerapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStoreOperations: DataStoreOperations
) {

    fun getAllHeroes(): Flow<PagingData<Hero>> {
        return remote.getAllHeroes()
    }

    fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return remote.searchHeroes(query)
    }

    suspend fun getSelectedHero(heroId: Int): Hero {
        return local.getSelectedHero(heroId)
    }

    suspend fun saveOnBoardingState(state: Boolean) {
        dataStoreOperations.saveOnBoardingState(state)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperations.readOnBoardingState()
    }
}