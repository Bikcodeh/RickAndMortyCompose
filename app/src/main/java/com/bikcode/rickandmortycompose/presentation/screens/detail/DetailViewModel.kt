package com.bikcode.rickandmortycompose.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.di.IoDispatcher
import com.bikcode.rickandmortycompose.domain.model.toEpisodeDto
import com.bikcode.rickandmortycompose.domain.use_case.GetEpisodesUC
import com.bikcode.rickandmortycompose.domain.use_case.GetSelectedCharacterUC
import com.bikcode.rickandmortycompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSelectedCharacterUC: GetSelectedCharacterUC,
    private val getEpisodesUC: GetEpisodesUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterSelected: MutableStateFlow<CharacterDTO?> = MutableStateFlow(null)
    val characterSelected: StateFlow<CharacterDTO?> = _characterSelected

    var state by mutableStateOf(DetailState())

    fun getSelectedCharacter(characterId: Int) {
        viewModelScope.launch(dispatcher) {
            _characterSelected.value =
                getSelectedCharacterUC(characterId = characterId)?.toCharacterDTO()
        }
    }

    fun getEpisodes(episodesUrl: List<String>) {
        viewModelScope.launch {
            getEpisodesUC(episodesUrl).collect {
                when (it) {
                    is Resource.Error -> {
                        state = state.copy(error = it.message)
                    }
                    is Resource.Loading -> state = state.copy(isLoading = it.isLoading)
                    is Resource.Success -> {
                        it.data?.let { episodes ->
                            state = state.copy(episodes = episodes.map { episode -> episode.toEpisodeDto() })
                        }
                    }
                }
            }
        }
    }


}