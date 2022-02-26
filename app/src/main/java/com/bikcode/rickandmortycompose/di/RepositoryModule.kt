package com.bikcode.rickandmortycompose.di

import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.data.repository.CharacterRepositoryImpl
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun characterRepositoryProvider(characterService: CharacterService): CharacterRepository {
        return CharacterRepositoryImpl(characterService)
    }
}