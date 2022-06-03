package com.bikcode.rickandmortycompose.di

import androidx.paging.ExperimentalPagingApi
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.data.remote.EpisodeService
import com.bikcode.rickandmortycompose.data.repository.CharacterRepositoryImpl
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import com.bikcode.rickandmortycompose.domain.repository.RemoteDataSource
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
        remoteDataSource: RemoteDataSource,
        rickAndMortyDatabase: RickAndMortyDatabase,
        localDataSource: LocalDataSource
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            remoteDataSource = remoteDataSource,
            rickAndMortyDatabase = rickAndMortyDatabase,
            localDataSource = localDataSource
        )
    }
}