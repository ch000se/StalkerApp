package com.example.stalkerapp.data.remote

import com.example.stalkerapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StalkerApi {

    @GET("/stalker/heroes")
    suspend fun getAllHeroes(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/stalker/heroes/search")
    suspend fun searchHeroes(
        @Query("name") name: String
    ): ApiResponse
}