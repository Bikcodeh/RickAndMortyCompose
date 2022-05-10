package com.bikcode.rickandmortycompose.data.repository

import com.bikcode.rickandmortycompose.data.model.ApiResponse
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.domain.repository.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val characterService: CharacterService) :
    RemoteDataSource {

    override suspend fun getAllCharacters(page: Int): ApiResponse<CharacterDTO> {
        return characterService.getAllCharacters(page)
    }
}