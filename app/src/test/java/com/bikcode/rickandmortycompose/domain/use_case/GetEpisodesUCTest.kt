package com.bikcode.rickandmortycompose.domain.use_case

import com.bikcode.rickandmortycompose.domain.model.Episode
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetEpisodesUCTest {

    private lateinit var getEpisodesUC: GetEpisodesUC


    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @MockK
    lateinit var characterRepository: CharacterRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        getEpisodesUC = GetEpisodesUC(characterRepository = characterRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a list of episodes in string should return a list of episodes model`() = runTest {

        //Given
        val episodes = listOf("1", "2")
        val response = listOf(
            Episode(
                name = "1",
                airDate = "2",
                episode = "3"
            )
        )
        coEvery { characterRepository.getEpisode(episodes) } returns flow {
            emit(
                Resource.Success(
                    response
                )
            )
        }

        //When
        val result = getEpisodesUC(listOf("1", "2"))

        //Then
        result.collect {

            Assert.assertEquals(
                listOf(
                    Episode(
                        name = "1",
                        airDate = "2",
                        episode = "3"
                    )
                ),
                it.data
            )
        }

        coVerify { characterRepository.getEpisode(episodes) }
    }

    @Test
    fun `given a list of episodes in string should return a error`() = runTest {

        //Given
        val episodes = listOf("1", "2")

        coEvery { characterRepository.getEpisode(episodes) } returns flow {
            emit(Resource.Error("Error handle data"))
        }

        //When
        val result = getEpisodesUC(episodes)

        //Then
        result.collect {
            Assert.assertEquals(
                "Error handle data",
                it.message
            )
        }

        coVerify { characterRepository.getEpisode(episodes) }
    }
}