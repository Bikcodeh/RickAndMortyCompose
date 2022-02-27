package com.bikcode.rickandmortycompose.di

import android.content.Context
import androidx.room.Room
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.local.dao.CharacterDao
import com.bikcode.rickandmortycompose.data.repository.LocalDataSourceImpl
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context): RickAndMortyDatabase =
        Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            RickAndMortyDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun characterDaoProvider(rickAndMortyDatabase: RickAndMortyDatabase): CharacterDao =
        rickAndMortyDatabase.characterDao()

    @Provides
    @Singleton
    fun localDataSourceProvider(characterDao: CharacterDao): LocalDataSource {
        return LocalDataSourceImpl(characterDao = characterDao)
    }
}