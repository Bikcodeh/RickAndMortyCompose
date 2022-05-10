package com.bikcode.rickandmortycompose.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.local.dao.CharacterDao
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalDataSourceImplTest {

    private val testDispatcher = StandardTestDispatcher()

    lateinit var characterDao: CharacterDao
    lateinit var database: RickAndMortyDatabase
    lateinit var localDataSource: LocalDataSource
    private val character = Character(
        created = "",
        episode = listOf(),
        gender = "",
        id = 1,
        image = "",
        location = Location(name = "", url = ""),
        name = "test",
        origin = Origin(name = "", url = ""),
        species = "",
        status = "alive",
        type = "",
        url = ""
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RickAndMortyDatabase::class.java)
            .allowMainThreadQueries().build()
        characterDao = database.characterDao()
        localDataSource = LocalDataSourceImpl(characterDao)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        Dispatchers.resetMain()
        database.close()
    }

    @Test
    fun insertAll_should_insert_a_list_of_characters() = runBlocking {
        characterDao.insertAll(
            listOf(
                character
            )
        )

        val result = localDataSource.getAllCharacters()

        Assert.assertFalse(result.invalid)
    }

    @Test
    fun getSelectedCharacter_given_a_specific_id_should_return_a_character() = runBlocking {
        //Given
        characterDao.insertAll(
            listOf(
                character
            )
        )

        //When
        val result = localDataSource.getSelectedCharacter(1)

        //Then
        Assert.assertEquals(
            character,
            result
        )
    }

    @Test
    fun getSelectedCharacter_given_a_non_existing_id_should_return_a_null_character() =
        runBlocking {
            //When
            val result = localDataSource.getSelectedCharacter(2)

            //Then
            Assert.assertEquals(
                null,
                result
            )
        }

    @Test
    fun searchCharacters_given_a_string_should_return_a_list_of_characters() = runBlocking {
        //Given
        characterDao.insertAll(
            listOf(
                character,
                character.copy(name = "testDummy", id = 2)
            )
        )

        val text = "test"

        //When
        val result = localDataSource.searchCharacters("%$text%")

        //This is a another correct way that it works
        /* val job = launch {
             result.collect {
                 Assert.assertEquals(
                     listOf(character, character.copy(name = "testDummy", id = 2)),
                     it
                 )
             }
         }
         job.cancel()*/
        //Then
        result.test {
            val data = awaitItem()
            Assert.assertEquals(
                listOf(character, character.copy(name = "testDummy", id = 2)),
                data
            )
        }
    }

    @Test
    fun searchCharacters_given_a_non_existing_string_should_return_a_empty_list_of_characters() = runBlocking {
        //Given
        characterDao.insertAll(
            listOf(
                character,
                character.copy(name = "testDummy", id = 2)
            )
        )

        val text = "none"

        //When
        val result = localDataSource.searchCharacters("%$text%")

        //Then
        result.test {
            val data = awaitItem()
            Assert.assertEquals(
                emptyList<Character>(),
                data
            )
        }
    }

    @Test
    fun getCharactersByStatus_given_a_status_should_return_a_list_of_characters() = runBlocking {
        //Given
        characterDao.insertAll(
            listOf(
                character,
                character.copy(name = "dummyTest", id = 2)
            )
        )

        //When
        val result = localDataSource.getCharactersByStatus(character.status)
        val data = result.take(2).toList()

        //Then
        Assert.assertEquals(
            listOf(character, character.copy(name = "dummyTest", id = 2)),
            data
        )
    }

    @Test
    fun getCharactersByStatus_given_a_status_non_existing_status_should_return_a_list_of_characters() = runBlocking {
        //Given
        characterDao.insertAll(
            listOf(
                character,
                character.copy(name = "dummyTest", id = 2)
            )
        )

        //When
        val result = localDataSource.getCharactersByStatus("none")
        val data = result.take(2).toList()

        //Then
        Assert.assertEquals(
            emptyList<Character>(),
            data
        )
    }
}