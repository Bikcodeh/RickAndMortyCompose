package com.bikcode.rickandmortycompose.data.remote

import com.bikcode.rickandmortycompose.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse
}