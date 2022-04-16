package com.bikcode.rickandmortycompose.data.remote

import com.bikcode.rickandmortycompose.data.model.EpisodeDTO
import retrofit2.http.GET
import retrofit2.http.Url

interface EpisodeService {

    @GET
    suspend fun getEpisode(
        @Url episodeUrl: String
    ): EpisodeDTO
}