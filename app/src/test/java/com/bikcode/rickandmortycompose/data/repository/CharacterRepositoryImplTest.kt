package com.bikcode.rickandmortycompose.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.data.remote.EpisodeService
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import com.bikcode.rickandmortycompose.rules.CoroutineTestRule
import com.bikcode.rickandmortycompose.util.Resource
import com.bikcode.rickandmortycompose.util.character
import com.bikcode.rickandmortycompose.util.episodeDTO
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class CharacterRepositoryImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var repository: CharacterRepository

    @RelaxedMockK
    lateinit var characterService: CharacterService

    @RelaxedMockK
    lateinit var episodeService: EpisodeService

    @RelaxedMockK
    lateinit var rickAndMortyDatabase: RickAndMortyDatabase

    @RelaxedMockK
    lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CharacterRepositoryImpl(
            characterService,
            episodeService,
            rickAndMortyDatabase,
            localDataSource
        )
    }

    @Test
    fun `getSelectedCharacter should return a specific character by id`() = runTest {
        //Given
        coEvery { localDataSource.getSelectedCharacter(1) } returns character

        //When
        val result = repository.getSelectedCharacter(1)

        //Then
        Assert.assertEquals(
            character,
            result
        )
        coVerify(exactly = 1) { repository.getSelectedCharacter(1) }
    }

    @Test
    fun `getSelectedCharacter should return a null character with undefined id`() = runTest {
        //Given
        val slot = slot<Int>()
        coEvery { localDataSource.getSelectedCharacter(capture(slot)) } returns null

        //When
        val result = repository.getSelectedCharacter(-1)

        //Then
        Assert.assertEquals(
            null,
            result
        )
        Assert.assertEquals(
            -1,
            slot.captured
        )

        coVerify(exactly = 1) { repository.getSelectedCharacter(-1) }
    }

    @Test
    fun `searchCharacters given a string should return a list of characters`() = runTest {
        //Given
        val slot = slot<String>()
        every { localDataSource.searchCharacters(capture(slot)) } returns flow {
            emit(
                listOf(
                    character
                )
            )
        }

        //When
        val result = repository.searchCharacters("test")

        result.test {
            val data = awaitItem()
            Assert.assertEquals(
                listOf(character),
                data
            )
            awaitComplete()
        }

        Assert.assertEquals(
            "%test%",
            slot.captured
        )
        verify { localDataSource.searchCharacters("%test%") }
    }

    @Test
    fun `searchCharacters given a undefined string should return a empty list of characters`() =
        runTest {
            //Given
            val slot = slot<String>()
            every { localDataSource.searchCharacters(capture(slot)) } returns flow {
                emit(
                    emptyList()
                )
            }

            //When
            val result = repository.searchCharacters("test")

            //Then
            result.test {
                val data = awaitItem()
                Assert.assertEquals(
                    emptyList<Character>(),
                    data
                )
                awaitComplete()
            }

            Assert.assertEquals(
                "%test%",
                slot.captured
            )
            verify { localDataSource.searchCharacters("%test%") }
        }

    @Test
    fun `getCharactersByStatus given a status should return a list of characters`() = runTest {
        //Given
        val slot = slot<String>()
        coEvery { localDataSource.getCharactersByStatus(capture(slot)) } returns listOf(character)

        //When
        val result = repository.getCharactersByStatus("all")

        //Then
        Assert.assertEquals(
            listOf(character),
            result
        )
        Assert.assertEquals(
            "all",
            slot.captured
        )
        coVerify { localDataSource.getCharactersByStatus("all") }
    }

    @Test
    fun `getCharactersByStatus given a non-existent status should return a empty list of characters`() =
        runTest {
            //Given
            val slot = slot<String>()
            coEvery { localDataSource.getCharactersByStatus(capture(slot)) } returns emptyList<Character>()

            //When
            val result = repository.getCharactersByStatus("none")

            //Then
            Assert.assertEquals(
                emptyList<Character>(),
                result
            )
            Assert.assertEquals(
                "none",
                slot.captured
            )
            coVerify { localDataSource.getCharactersByStatus("none") }
        }

    @Test
    fun `getEpisode given a list of urls of episodes should return a list of episodes`() = runTest {
        //Given
        coEvery { episodeService.getEpisode("test") } returns episodeDTO

        //When
        val result = repository.getEpisode(listOf("test"))

        //Then
        result.test {
            val loading = awaitItem()
            val data = awaitItem()
            val loadingFinish = awaitItem()
            Assert.assertTrue(
                loading is Resource.Loading
            )
            Assert.assertTrue(
                data is Resource.Success
            )
            Assert.assertTrue(
                loadingFinish is Resource.Loading
            )
            awaitComplete()
        }
        coVerify { episodeService.getEpisode("test") }
    }

    @Test
    fun `getEpisode should catch a IOException and return a error resource`() = runTest {
        //Given
        coEvery { episodeService.getEpisode("test") }.throws(IOException("Error"))

        //When
        val result = repository.getEpisode(listOf("test"))

        //Then
        result.test {
            val loading = awaitItem()
            val data = awaitItem()
            Assert.assertTrue(
                loading is Resource.Loading
            )
            Assert.assertTrue(
                data is Resource.Error
            )
            Assert.assertEquals(
                "Couldn't load episodes",
                data.message
            )
            awaitComplete()
        }
        coVerify { episodeService.getEpisode("test") }
    }

    @Test
    fun `getEpisode should catch a HttpException and return a error resource`() = runTest {
        //Given
        val exception = mockk<HttpException>()
        every { exception.printStackTrace() } returns Unit
        coEvery { episodeService.getEpisode("test") }.throws(exception)

        //When
        val result = repository.getEpisode(listOf("test"))

        //Then
        result.test {
            val loading = awaitItem()
            val data = awaitItem()
            Assert.assertTrue(
                loading is Resource.Loading
            )
            Assert.assertTrue(
                data is Resource.Error
            )
            Assert.assertEquals(
                "Couldn't load episodes",
                data.message
            )
            awaitComplete()
        }
        coVerify { episodeService.getEpisode("test") }
        verify { exception.printStackTrace() }
    }

    @Test
    fun `getEpisode should catch a unexpected exception and return a error resource`() = runTest {
        //Given
        coEvery { episodeService.getEpisode("test") }.throws(Exception("Error"))

        //When
        val result = repository.getEpisode(listOf("test"))

        //Then
        result.test {
            val loading = awaitItem()
            val data = awaitItem()
            Assert.assertTrue(
                loading is Resource.Loading
            )
            Assert.assertTrue(
                data is Resource.Error
            )
            Assert.assertEquals(
                "Unexpected error",
                data.message
            )
            awaitComplete()
        }
        coVerify { episodeService.getEpisode("test") }
    }
}