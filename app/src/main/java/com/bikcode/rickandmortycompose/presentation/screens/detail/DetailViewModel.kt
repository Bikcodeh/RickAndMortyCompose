package com.bikcode.rickandmortycompose.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.use_case.GetSelectedCharacterUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getSelectedCharacterUC: GetSelectedCharacterUC) :
    ViewModel() {

    private val _characterSelected: MutableStateFlow<CharacterDTO?> = MutableStateFlow(null)
    val characterSelected: StateFlow<CharacterDTO?> = _characterSelected

    fun getSelectedCharacter(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _characterSelected.value = getSelectedCharacterUC(characterId = characterId)?.toCharacterDTO()
        }
    }
}