package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val getAllCharacters = characterRepository.getAllCharacters()
}