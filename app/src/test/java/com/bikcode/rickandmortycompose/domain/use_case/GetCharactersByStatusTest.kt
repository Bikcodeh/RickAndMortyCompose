package com.bikcode.rickandmortycompose.domain.use_case

import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.util.character
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
class GetCharactersByStatusTest {

    private lateinit var getCharactersByStatus: GetCharactersByStatus

    @MockK
    lateinit var characterRepository: CharacterRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        getCharactersByStatus = GetCharactersByStatus(characterRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a status should return a character list`() = runTest {
        //Given
        val expectedResult = listOf(
            character,
            character.copy(name = "Dummy 2"),
            character.copy(name = "Dummy 3")
        )

        coEvery { characterRepository.getCharactersByStatus("alive") } returns expectedResult

        //When
        val result = getCharactersByStatus("alive")

        //Then
        Assert.assertEquals(
            expectedResult,
            result
        )
    }

    @Test
    fun `given a undefined status should return a empty list`() = runTest {
        //Given
        coEvery { characterRepository.getCharactersByStatus("undefined") } returns emptyList()

        //When
        val result = getCharactersByStatus("undefined")

        //Then
        Assert.assertEquals(
            emptyList<Character>(),
            result
        )
    }
}