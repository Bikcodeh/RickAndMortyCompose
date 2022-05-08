package com.bikcode.rickandmortycompose.util

import androidx.paging.PagingData
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Episode
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeCharacterRepository : CharacterRepository {

    private var error = false

    fun shouldReturnError(error: Boolean) {
        this.error = error
    }

    override fun getAllCharacters(): Flow<PagingData<Character>> {
        return flowOf(PagingData.from(listOf(character)))
    }

    override suspend fun getSelectedCharacter(characterId: Int): Character? {
        return if(error) null else character
    }

    override fun searchCharacters(text: String): Flow<List<Character>> {
        return flowOf(listOf(character, character.copy(name = "test")))
    }

    override suspend fun getEpisode(episodesUrl: List<String>): Flow<Resource<List<Episode>>> {
        return flow {
            if(error){
                emit(Resource.Error("Error fetching data"))
            } else {
                emit(Resource.Success(listOf(Episode(name = "test", airDate = "", episode = ""))))
            }
        }
    }

    override suspend fun getCharactersByStatus(status: String): List<Character> {
        return listOf(character)
    }
}