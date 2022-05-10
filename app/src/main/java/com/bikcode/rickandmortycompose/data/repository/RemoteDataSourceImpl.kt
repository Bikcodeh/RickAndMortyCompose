package com.bikcode.rickandmortycompose.data.repository

import com.bikcode.rickandmortycompose.data.model.ApiResponse
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.data.model.EpisodeDTO
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.data.remote.EpisodeService
import com.bikcode.rickandmortycompose.domain.repository.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val characterService: CharacterService,
    private val episodeService: EpisodeService
) : RemoteDataSource {

    override suspend fun getAllCharacters(page: Int): ApiResponse<CharacterDTO> {
        return characterService.getAllCharacters(page)
    }

    override suspend fun getEpisode(url: String): EpisodeDTO {
        return episodeService.getEpisode(url)
    }
}