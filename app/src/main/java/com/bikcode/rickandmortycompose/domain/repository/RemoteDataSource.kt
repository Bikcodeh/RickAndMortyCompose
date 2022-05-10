package com.bikcode.rickandmortycompose.domain.repository

import com.bikcode.rickandmortycompose.data.model.ApiResponse
import com.bikcode.rickandmortycompose.data.model.CharacterDTO

interface RemoteDataSource {

    suspend fun getAllCharacters(page: Int = 1): ApiResponse<CharacterDTO>
}