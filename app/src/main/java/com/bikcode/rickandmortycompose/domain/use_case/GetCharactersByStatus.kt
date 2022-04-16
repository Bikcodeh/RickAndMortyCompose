package com.bikcode.rickandmortycompose.domain.use_case

import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersByStatus @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(status: String): List<Character> =
        characterRepository.getCharactersByStatus(status = status)
}