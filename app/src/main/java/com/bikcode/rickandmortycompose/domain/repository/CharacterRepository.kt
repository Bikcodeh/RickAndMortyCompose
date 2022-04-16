package com.bikcode.rickandmortycompose.domain.repository

import androidx.paging.PagingData
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Episode
import com.bikcode.rickandmortycompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<PagingData<Character>>
    suspend fun getSelectedCharacter(characterId: Int): Character?
    fun searchCharacters(text: String): Flow<List<Character>>
    suspend fun getEpisode(episodesUrl: List<String>): Flow<Resource<List<Episode>>>
}