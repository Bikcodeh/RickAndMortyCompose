package com.bikcode.rickandmortycompose.presentation.screens.detail

import com.bikcode.rickandmortycompose.data.model.EpisodeDTO
import com.bikcode.rickandmortycompose.domain.model.Episode
import com.bikcode.rickandmortycompose.domain.use_case.GetEpisodesUC
import com.bikcode.rickandmortycompose.domain.use_case.GetSelectedCharacterUC
import com.bikcode.rickandmortycompose.rules.CoroutineTestRule
import com.bikcode.rickandmortycompose.util.Resource
import com.bikcode.rickandmortycompose.util.character
import com.bikcode.rickandmortycompose.util.characterDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.*

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var getSelectedCharacterUC: GetSelectedCharacterUC

    @RelaxedMockK
    lateinit var getEpisodesUC: GetEpisodesUC

    private lateinit var detailViewModel: DetailViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(
            getSelectedCharacterUC,
            getEpisodesUC,
            dispatcher
        )
    }

    @Test
    fun `getCharacterSelected should return a specific character`() = runTest {
        //Given
        coEvery { getSelectedCharacterUC(1) } returns character

        //When
        detailViewModel.getSelectedCharacter(1)

        //Then
        Assert.assertEquals(
            characterDTO,
            detailViewModel.characterSelected.value
        )
        coVerify { getSelectedCharacterUC(1) }
    }

    @Test
    fun `getCharacterSelected should return null`() = runTest {
        //Given
        coEvery { getSelectedCharacterUC(1) } returns null

        //When
        detailViewModel.getSelectedCharacter(1)

        //Then
        Assert.assertEquals(
            null,
            detailViewModel.characterSelected.value
        )
        coVerify { getSelectedCharacterUC(1) }
    }

    @Test
    fun `getEpisodes should return all the data`() = runTest {
        val expectedResponse = listOf(EpisodeDTO(name = "test", airDate = "", episode = ""))
        //Given
        coEvery { getEpisodesUC(listOf("1", "2")) } returns flow {
            emit(Resource.Success(listOf(Episode(name = "test", airDate = "", episode = ""))))
        }

        //When
        detailViewModel.getEpisodes(listOf("1", "2"))

        //Then
        Assert.assertEquals(
            expectedResponse,
            detailViewModel.state.episodes
        )
        coVerify { getEpisodesUC(listOf("1", "2")) }
    }

    @Test
    fun `getEpisodes should return null data`() = runTest {
        //Given
        coEvery { getEpisodesUC(listOf("1", "2")) } returns flow {
            emit(Resource.Success(data = null))
        }

        //When
        detailViewModel.getEpisodes(listOf("1", "2"))

        //Then
        Assert.assertEquals(
            emptyList<EpisodeDTO>(),
            detailViewModel.state.episodes
        )
        coVerify { getEpisodesUC(listOf("1", "2")) }
    }

    @Test
    fun `getEpisodes should return a loading state`() = runTest {
        //Given
        coEvery { getEpisodesUC(listOf("1", "2")) } returns flow {
            emit(Resource.Loading(isLoading = true))
        }

        //When
        detailViewModel.getEpisodes(listOf("1", "2"))

        //Then
        Assert.assertEquals(
            true,
            detailViewModel.state.isLoading
        )
        coVerify { getEpisodesUC(listOf("1", "2")) }
    }

    @Test
    fun `getEpisodes should return a error state`() = runTest {
        //Given
        coEvery { getEpisodesUC(listOf("1", "2")) } returns flow {
            emit(Resource.Error("Ups! Something Happened"))
        }

        //When
        detailViewModel.getEpisodes(listOf("1", "2"))

        //Then
        Assert.assertEquals(
            "Ups! Something Happened",
            detailViewModel.state.error
        )
        coVerify { getEpisodesUC(listOf("1", "2")) }
    }

    @Test
    fun `state should return a default state`() {
        Assert.assertEquals(
            DetailState(),
            detailViewModel.state
        )
    }
}