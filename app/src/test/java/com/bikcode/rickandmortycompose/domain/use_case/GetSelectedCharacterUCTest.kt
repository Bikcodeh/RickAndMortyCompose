package com.bikcode.rickandmortycompose.domain.use_case

import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.util.character
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(
    ExperimentalCoroutinesApi::class,
    DelicateCoroutinesApi::class
)
class GetSelectedCharacterUCTest {

    private lateinit var getSelectedCharacterUC: GetSelectedCharacterUC

    @MockK
    lateinit var repository: CharacterRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        getSelectedCharacterUC = GetSelectedCharacterUC(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a specific character id should return a character`() = runTest {
        //Given
        coEvery { repository.getSelectedCharacter(1) } returns character

        //When
        val result = getSelectedCharacterUC(1)

        //Then
        Assert.assertEquals(
            character,
            result
        )

        coVerify(exactly = 1) { repository.getSelectedCharacter(1) }
    }
    @Test
    fun `given a specific character id that doesn't exist should return null`() = runTest {
        //Given
        coEvery { repository.getSelectedCharacter(1) } returns null

        //When
        val result = getSelectedCharacterUC(1)

        //Then
        Assert.assertEquals(
            null,
            result
        )

        coVerify(exactly = 1) { repository.getSelectedCharacter(1) }
    }
}