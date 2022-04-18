package com.bikcode.rickandmortycompose.data.model

import com.bikcode.rickandmortycompose.domain.model.Episode
import com.google.gson.annotations.SerializedName

data class EpisodeDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String
)

fun EpisodeDTO.toEpisode(): Episode = Episode(name, airDate, episode)