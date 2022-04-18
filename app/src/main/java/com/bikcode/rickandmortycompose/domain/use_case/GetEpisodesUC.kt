package com.bikcode.rickandmortycompose.domain.use_case

import com.bikcode.rickandmortycompose.domain.model.Episode
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEpisodesUC @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(episodesUrl: List<String>): Flow<Resource<List<Episode>>> {
        return characterRepository.getEpisode(episodesUrl)
    }
}