package com.example.stalkerapp.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.stalkerapp.data.local.StalkerDatabase
import com.example.stalkerapp.data.remote.StalkerApi
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.domain.model.HeroRemoteKeys
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val stalkerApi: StalkerApi,
    private val stalkerDatabase: StalkerDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = stalkerDatabase.heroDao()
    private val heroRemoteKeysDao = stalkerDatabase.heroRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 60 * 24 // 24 години (у хвилинах)

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        Log.d("HeroRemoteMediator", "load() called with loadType: $loadType")

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("HeroRemoteMediator", "Refreshing data")
                    val remoteKeys = getClosestRemoteKeys(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    Log.d("HeroRemoteMediator", "Loading previous page")
                    val remoteKeys = getFirstRemoteKeys(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                    remoteKeys.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                }
                LoadType.APPEND -> {
                    Log.d("HeroRemoteMediator", "Loading next page")
                    val remoteKeys = getLastRemoteKeys(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                    remoteKeys.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                }
            }

            Log.d("HeroRemoteMediator", "Fetching data for page: $page")
            val response = stalkerApi.getAllHeroes()

            Log.d("HeroRemoteMediator", "API Response: $response")

            if (response.heroes.isNotEmpty()) {
                Log.d("HeroRemoteMediator", "Inserting data into database")
                stalkerDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteALLRemoteKeys()
                        Log.d("HeroRemoteMediator", "Cleared database for refresh")
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map {
                        HeroRemoteKeys(id = it.id, prevPage = prevPage, nextPage = nextPage, lastUpdated = response.lastUpdated)
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(keys)
                    heroDao.addHero(response.heroes)
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)

        } catch (e: Exception) {
            Log.e("HeroRemoteMediator", "Error during load: ${e.message}", e)

            // Перевіряємо на помилку мережі, якщо вона сталася, використовуємо локальні дані
            if (e is java.net.UnknownHostException || e is java.net.SocketTimeoutException) {
                Log.d("HeroRemoteMediator", "Network error, fetching from local DB")

                // Перевіряємо кількість героїв в локальній базі
                val heroCount = heroDao.getHeroCount()
                if (heroCount > 0) {
                    // Якщо є локальні герої, повертаємо їх
                    val localHeroes = heroDao.getAllHeroes()  // Отримуємо локальні герої
                    return MediatorResult.Success(endOfPaginationReached = false)
                } else {
                    // Якщо локальних героїв немає, повертаємо помилку
                    return MediatorResult.Error(e)
                }
            }

            // Інші помилки обробляються як звичайні
            MediatorResult.Error(e)
        }
    }

    private suspend fun getLastRemoteKeys(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(hero.id)
        }
    }

    private suspend fun getFirstRemoteKeys(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(hero.id)
        }
    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { heroId ->
                heroRemoteKeysDao.getRemoteKeys(heroId)
            }
        }
    }

}
