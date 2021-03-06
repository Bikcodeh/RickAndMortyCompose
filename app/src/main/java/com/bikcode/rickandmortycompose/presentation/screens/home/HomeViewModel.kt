package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.di.IoDispatcher
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _searchedCharacters = MutableStateFlow<List<CharacterDTO>>(emptyList())
    val searchedCharacters = _searchedCharacters.asStateFlow()

    private val _filteredCharacters = MutableStateFlow<List<CharacterDTO>>(emptyList())
    val filteredCharacters = _filteredCharacters.asStateFlow()

    private var searchJob: Job? = null

    val getAllCharacters =
        characterRepository.getAllCharacters().cachedIn(viewModelScope).map {
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
            viewModelScope.launch(dispatcher) {
                characterRepository.searchCharacters(text = text)
                    .map { it.map { character -> character.toCharacterDTO() } }
                    .collect {
                        _searchedCharacters.value = it
                    }
            }
        }
    }
}