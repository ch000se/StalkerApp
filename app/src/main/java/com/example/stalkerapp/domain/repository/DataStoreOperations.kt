package com.example.stalkerapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnBoardingState(state: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}