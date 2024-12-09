package com.example.stalkerapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.stalkerapp.data.local.StalkerDatabase
import com.example.stalkerapp.data.paging_source.HeroRemoteMediator
import com.example.stalkerapp.data.paging_source.SearchHeroesSource
import com.example.stalkerapp.data.remote.StalkerApi
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.domain.repository.RemoteDataSource
import com.example.stalkerapp.util.Constants.ITEMS_FOR_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val stalkerApi: StalkerApi,
    private val stalkerDatabase: StalkerDatabase
): RemoteDataSource {
    private val heroDao = stalkerDatabase.heroDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_FOR_PAGE,
            ),
            remoteMediator = HeroRemoteMediator(stalkerApi, stalkerDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_FOR_PAGE,
            ),
            pagingSourceFactory = {
                SearchHeroesSource(stalkerApi, query)
            }
        ).flow
    }
}