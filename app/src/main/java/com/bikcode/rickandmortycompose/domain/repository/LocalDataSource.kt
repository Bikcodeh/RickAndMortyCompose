package com.bikcode.rickandmortycompose.domain.repository

import androidx.paging.PagingSource
import com.bikcode.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllCharacters(): PagingSource<Int, Character>
    suspend fun getSelectedCharacter(characterId: Int): Character?
    fun searchCharacters(text: String): Flow<List<Character>>
    suspend fun getCharactersByStatus(status: String): List<Character>
}