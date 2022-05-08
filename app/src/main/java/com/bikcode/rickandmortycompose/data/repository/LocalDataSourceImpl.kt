package com.bikcode.rickandmortycompose.data.repository

import androidx.paging.PagingSource
import com.bikcode.rickandmortycompose.data.local.dao.CharacterDao
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val characterDao: CharacterDao) : LocalDataSource {
    override fun getAllCharacters(): PagingSource<Int, Character> {
        return characterDao.getAllCharacters()
    }

    override suspend fun getSelectedCharacter(characterId: Int): Character? {
        return characterDao.getSelectedCharacter(characterId = characterId)
    }

    override fun searchCharacters(text: String): Flow<List<Character>> {
        return characterDao.searchCharacters(text = text)
    }

    override suspend fun getCharactersByStatus(status: String): List<Character> {
        return characterDao.getCharactersByStatus(status = status)
    }
}