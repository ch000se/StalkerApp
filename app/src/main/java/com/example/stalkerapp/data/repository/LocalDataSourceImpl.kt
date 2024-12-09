package com.example.stalkerapp.data.repository

import com.example.stalkerapp.data.local.StalkerDatabase
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(stalkerDatabase: StalkerDatabase): LocalDataSource{

    private val heroDao = stalkerDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId)
    }
}