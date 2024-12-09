package com.example.stalkerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stalkerapp.data.local.dao.HeroDao
import com.example.stalkerapp.data.local.dao.HeroRemoteKeysDao
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class StalkerDatabase: RoomDatabase(){

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao

}