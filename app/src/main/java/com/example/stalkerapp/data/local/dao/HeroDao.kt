package com.example.stalkerapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stalkerapp.domain.model.Hero

@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id ASC")
    fun getAllHeroes(): PagingSource<Int, Hero>

    @Query("SELECT * FROM hero_table WHERE id = :heroId")
    fun getSelectedHero(heroId: Int): Hero

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHero(heroes: kotlin.collections.List<com.example.stalkerapp.domain.model.Hero>)

    @Query("DELETE FROM hero_table")
    suspend fun deleteAllHeroes()

    @Query("SELECT COUNT(*) FROM hero_table")
    suspend fun getHeroCount(): Int

}