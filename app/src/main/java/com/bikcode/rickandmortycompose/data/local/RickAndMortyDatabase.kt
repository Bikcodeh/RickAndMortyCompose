package com.bikcode.rickandmortycompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bikcode.rickandmortycompose.data.local.dao.CharacterDao
import com.bikcode.rickandmortycompose.data.local.dao.RemoteKeysDao
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.RemoteKeys

@Database(entities = [Character::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class RickAndMortyDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_NAME = "rick_and_morty.db"
    }
}