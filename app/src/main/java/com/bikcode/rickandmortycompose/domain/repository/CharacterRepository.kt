package com.bikcode.rickandmortycompose.domain.repository

import androidx.paging.PagingData
import com.bikcode.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<PagingData<Character>>
}