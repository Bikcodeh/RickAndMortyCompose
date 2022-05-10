package com.bikcode.rickandmortycompose.data.repository

import androidx.paging.PagingSource
import com.bikcode.rickandmortycompose.data.model.ApiResponse
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.data.model.EpisodeDTO
import com.bikcode.rickandmortycompose.data.model.InfoDTO
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.bikcode.rickandmortycompose.domain.repository.RemoteDataSource
import com.bikcode.rickandmortycompose.util.episodeDTO
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CharacterPagingSourceTest {

    private lateinit var characterPagingSource: CharacterPagingSource

    private val characters = mutableListOf<Character>().apply {
        add(
            Character(
                created = "",
                episode = listOf(),
                gender = "",
                id = 1,
                image = "",
                location = Location(name = "", url = ""),
                name = "1",
                origin = Origin(name = "", url = ""),
                species = "",
                status = "",
                type = "",
                url = ""
            )
        )
        add(
            Character(
                created = "",
                episode = listOf(),
                gender = "",
                id = 2,
                image = "",
                location = Location(name = "", url = ""),
                name = "2",
                origin = Origin(name = "", url = ""),
                species = "",
                status = "",
                type = "",
                url = ""
            )
        )
        add(
            Character(
                created = "",
                episode = listOf(),
                gender = "",
                id = 3,
                image = "",
                location = Location(name = "", url = ""),
                name = "3",
                origin = Origin(name = "", url = ""),
                species = "",
                status = "",
                type = "",
                url = ""
            )
        )
    }

    private val exceptionIO = IOException("Error")

    private val exceptionHttp = HttpException(
        Response.error<Any>(
            409,
            "raw response body as string".toResponseBody("plain/text".toMediaTypeOrNull())
        )
    )

    private enum class Errors {
        NONE,
        RETURN_EMPTY,
        IO_EXCEPTION,
        HTTP_EXCEPTION
    }

    @Test
    fun `expect multiple characters, assert LoadResult_Page`() = runBlocking {
        characterPagingSource = CharacterPagingSource(
            FakeRemoteDataSourceImpl()
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Character>>(
            expected = PagingSource.LoadResult.Page(
                data = listOf(characters[0], characters[1], characters[2]),
                prevKey = null,
                nextKey = null,
            ),
            actual = characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `expect empty characters, assert LoadResult_Page`() = runBlocking {
        characterPagingSource = CharacterPagingSource(
            FakeRemoteDataSourceImpl(Errors.RETURN_EMPTY)
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Character>>(
            expected = PagingSource.LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null,
            ),
            actual = characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `getAllCharacter should catch a IOException, assert LoadResult_Error`() = runBlocking {
        characterPagingSource = CharacterPagingSource(
            FakeRemoteDataSourceImpl(Errors.IO_EXCEPTION)
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Character>>(
            expected = PagingSource.LoadResult.Error(
                throwable = exceptionIO
            ),
            actual = characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `getAllCharacter should catch a HttpException, assert LoadResult_Error`() = runBlocking {
        characterPagingSource = CharacterPagingSource(
            FakeRemoteDataSourceImpl(Errors.HTTP_EXCEPTION)
        )

        kotlin.test.assertEquals<PagingSource.LoadResult<Int, Character>>(
            expected = PagingSource.LoadResult.Error(
                throwable = exceptionHttp
            ),
            actual = characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 5,
                    placeholdersEnabled = false
                )
            )
        )
    }

    private inner class FakeRemoteDataSourceImpl(val error: Errors = Errors.NONE) :
        RemoteDataSource {

        override suspend fun getAllCharacters(page: Int): ApiResponse<CharacterDTO> {
            return when (error) {
                Errors.NONE -> {
                    return ApiResponse(
                        info = InfoDTO(
                            count = 1,
                            next = null,
                            prev = null,
                            pages = 0
                        ),
                        results = characters.map { it.toCharacterDTO() }
                    )
                }
                Errors.RETURN_EMPTY -> {
                    return ApiResponse(
                        info = InfoDTO(
                            count = 1,
                            next = null,
                            prev = null,
                            pages = 0
                        ),
                        results = emptyList()
                    )
                }
                Errors.IO_EXCEPTION -> {
                    throw exceptionIO
                }
                Errors.HTTP_EXCEPTION -> {
                    throw exceptionHttp
                }
            }
        }

        override suspend fun getEpisode(url: String): EpisodeDTO {
            return episodeDTO
        }
    }
}