package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _searchedCharacters = MutableStateFlow<List<CharacterDTO>>(emptyList())
    val searchedCharacters = _searchedCharacters

    private val _filteredCharacters = MutableStateFlow<List<CharacterDTO>>(emptyList())
    val filteredCharacters = _filteredCharacters

    var state by mutableStateOf(HomeState())
    private var searchJob: Job? = null

    val getAllCharacters = characterRepository.getAllCharacters().cachedIn(viewModelScope).map {
        it.map { character -> character.toCharacterDTO() }
    }


    fun filterCharacters(status: String) {
        viewModelScope.launch {
            if (status == "all") {
                _filteredCharacters.value = emptyList()
            } else {
                _filteredCharacters.value =
                    characterRepository.getCharactersByStatus(status = status)
                        .map { it.toCharacterDTO() }
            }
        }
    }

    fun searchCharacters(text: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            viewModelScope.launch(Dispatchers.IO) {
                characterRepository.searchCharacters(text = text)
                    /*.onStart {
                        state = state.copy(isLoading = true)
                    }
                    .catch { error ->
                        state = state.copy(error = error.message, isLoading = false)
                    }*/
                    .map { it.map { character -> character.toCharacterDTO() } }
                    .collect {
                        //state = state.copy(characters = it, isLoading = false, error = null)
                        _searchedCharacters.value = it
                    }
            }
        }
    }
}