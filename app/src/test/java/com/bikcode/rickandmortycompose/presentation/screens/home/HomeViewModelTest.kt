package com.bikcode.rickandmortycompose.presentation.screens.home

import app.cash.turbine.test
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.rules.CoroutineTestRule
import com.bikcode.rickandmortycompose.util.character
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var homeViewModel: HomeViewModel

    @MockK(relaxed = true)
    lateinit var characterRepository: CharacterRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(characterRepository, testDispatcher)
    }

    @Test
    fun `given a status should return a character list`() = runTest {
        //Given
        val response = listOf(character)

        coEvery { characterRepository.getCharactersByStatus("alive") } returns response

        //When
        homeViewModel.filterCharacters("alive")

        //Then
        homeViewModel.filteredCharacters.test {
            val data = awaitItem().first()
            Assert.assertTrue(data.name.isNotBlank())
            Assert.assertEquals(
                "Dummy",
                data.name
            )
        }
        coVerify { characterRepository.getCharactersByStatus("alive") }
    }

    @Test
    fun `given status all should return a empty list`() = runTest {
        //When
        homeViewModel.filterCharacters("all")

        //Then
        homeViewModel.filteredCharacters.test {
            val data = awaitItem()
            Assert.assertEquals(
                emptyList<CharacterDTO>(),
                data
            )
        }
    }

    @Test
    fun `given a string should return a character list`() = runTest {
        //Given
        val data = listOf(character)
        coEvery { characterRepository.searchCharacters("rick") } returns flowOf(data)

        //When
        homeViewModel.searchCharacters("rick")

        //Then
        Assert.assertEquals(
            "Dummy",
            homeViewModel.searchedCharacters.value.first().name
        )
        coVerify { characterRepository.searchCharacters("rick") }
    }

    @Test
    fun `given a string should return a empty list`() = runTest {
        //Given
        coEvery { characterRepository.searchCharacters("rick") } returns flowOf(emptyList<Character>())

        //When
        homeViewModel.searchCharacters("rick")

        //Then
        homeViewModel.searchedCharacters.test {
            val item = expectMostRecentItem()
            Assert.assertEquals(
                emptyList<CharacterDTO>(),
                item
            )
        }
        coVerify { characterRepository.searchCharacters("rick") }
    }
}