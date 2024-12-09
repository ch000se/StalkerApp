package com.example.stalkerapp.domain.repository

import com.example.stalkerapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}