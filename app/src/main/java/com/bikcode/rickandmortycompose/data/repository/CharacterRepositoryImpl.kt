package com.bikcode.rickandmortycompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val rickAndMortyDatabase: RickAndMortyDatabase
) :
    CharacterRepository {

    override fun getAllCharacters(): Flow<PagingData<Character>> {

        val characterSourceFactory = { rickAndMortyDatabase.characterDao().getAllCharacters() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                rickAndMortyDatabase = rickAndMortyDatabase,
                characterService = characterService
            ),
            pagingSourceFactory = characterSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}