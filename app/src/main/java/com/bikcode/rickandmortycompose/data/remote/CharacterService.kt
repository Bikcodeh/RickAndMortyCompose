package com.bikcode.rickandmortycompose.data.remote

import com.bikcode.rickandmortycompose.data.model.ApiResponse
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse<CharacterDTO>
}