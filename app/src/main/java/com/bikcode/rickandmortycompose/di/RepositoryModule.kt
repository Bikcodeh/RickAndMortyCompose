package com.bikcode.rickandmortycompose.di

import androidx.paging.ExperimentalPagingApi
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.data.remote.EpisodeService
import com.bikcode.rickandmortycompose.data.repository.CharacterRepositoryImpl
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
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
        rickAndMortyDatabase: RickAndMortyDatabase,
        episodeService: EpisodeService,
        localDataSource: LocalDataSource
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterService = characterService,
            episodeService = episodeService,
            rickAndMortyDatabase = rickAndMortyDatabase,
            localDataSource = localDataSource
        )
    }
}