package com.bikcode.rickandmortycompose.di

import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.domain.use_case.GetSelectedCharacterUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun getSelectedCharacterUseCaseProvider(characterRepository: CharacterRepository): GetSelectedCharacterUC =
        GetSelectedCharacterUC(characterRepository = characterRepository)
}