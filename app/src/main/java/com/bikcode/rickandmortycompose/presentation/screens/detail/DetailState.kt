package com.bikcode.rickandmortycompose.presentation.screens.detail

import com.bikcode.rickandmortycompose.data.model.EpisodeDTO

data class DetailState(
    val episodes: List<EpisodeDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
