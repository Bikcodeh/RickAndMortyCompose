package com.bikcode.rickandmortycompose.di

import androidx.paging.ExperimentalPagingApi
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.data.repository.CharacterRepositoryImpl
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun characterRepositoryProvider(
        characterService: CharacterService,
        rickAndMortyDatabase: RickAndMortyDatabase
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterService = characterService,
            rickAndMortyDatabase = rickAndMortyDatabase
        )
    }
}