package com.example.stalkerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stalkerapp.util.Constants.HERO_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false) // або true, якщо Room повинен генерувати ключ
    val id: Int, // Це поле стає первинним ключем
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String> = emptyList(),
    val abilities: List<String> = emptyList(),
    val natureTypes: List<String> = emptyList()
)

