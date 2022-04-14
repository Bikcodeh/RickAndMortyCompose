package com.bikcode.rickandmortycompose.data.repository

import com.bikcode.rickandmortycompose.data.local.dao.CharacterDao
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val characterDao: CharacterDao) : LocalDataSource {

    override suspend fun getSelectedCharacter(characterId: Int): Character? {
        return characterDao.getSelectedCharacter(characterId = characterId)
    }

    override fun searchCharacters(text: String): Flow<List<Character>> {
        return characterDao.searchCharacters(text = text)
    }
}