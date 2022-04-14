package com.bikcode.rickandmortycompose.presentation.screens.home

import com.bikcode.rickandmortycompose.data.model.CharacterDTO

data class HomeState(
    val characters: List<CharacterDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)