package com.bikcode.rickandmortycompose.domain.repository

import com.bikcode.rickandmortycompose.domain.model.Character

interface LocalDataSource {
    suspend fun getSelectedCharacter(characterId: Int): Character
}