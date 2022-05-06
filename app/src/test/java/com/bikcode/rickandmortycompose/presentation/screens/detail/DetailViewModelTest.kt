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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(
            getSelectedCharacterUC,
            getEpisodesUC
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
    fun getState() = runTest {
        val response = listOf(
            EpisodeDTO(name = "test", airDate = "", episode = "")
        )
        coEvery { getEpisodesUC(listOf("1", "2")) } returns flow {
            emit(Resource.Success(listOf(Episode(name = "test", airDate = "", episode = ""))))
        }

        detailViewModel.getEpisodes(listOf("1", ""))

        Assert.assertEquals(
            response,
            detailViewModel.state.episodes
        )

        coVerify { getEpisodesUC(listOf("1", "2")) }
    }
}