package com.bikcode.rickandmortycompose.domain.model

import com.bikcode.rickandmortycompose.data.model.EpisodeDTO

data class Episode(
    val name: String,
    val airDate: String,
    val episode: String
)

fun Episode.toEpisodeDto(): EpisodeDTO = EpisodeDTO(name, airDate, episode)