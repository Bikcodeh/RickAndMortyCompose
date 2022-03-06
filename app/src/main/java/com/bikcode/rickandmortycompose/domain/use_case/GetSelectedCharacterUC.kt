package com.bikcode.rickandmortycompose.domain.use_case

import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import javax.inject.Inject

class GetSelectedCharacterUC @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(characterId: Int): Character? {
        return characterRepository.getSelectedCharacter(characterId = characterId)
    }
}