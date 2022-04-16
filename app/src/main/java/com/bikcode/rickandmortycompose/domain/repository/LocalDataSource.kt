package com.bikcode.rickandmortycompose.domain.repository

import com.bikcode.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getSelectedCharacter(characterId: Int): Character?
    fun searchCharacters(text: String): Flow<List<Character>>
    suspend fun getCharactersByStatus(status: String): List<Character>
}