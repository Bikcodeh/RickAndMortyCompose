package com.bikcode.rickandmortycompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikcode.rickandmortycompose.domain.model.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Character>)

    @Query("SELECT * FROM character")
    fun getAllCharacters(): PagingSource<Int, Character>

    @Query("DELETE FROM character")
    suspend fun clear(): Unit
}