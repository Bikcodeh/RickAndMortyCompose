package com.bikcode.rickandmortycompose.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bikcode.rickandmortycompose.data.remote.CharacterService
import com.bikcode.rickandmortycompose.domain.model.Character
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val CHARACTER_STARTING_PAGE_INDEX = 1

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition = anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition = anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: CHARACTER_STARTING_PAGE_INDEX
        return try {
            val response = characterService.getAllCharacters(page = position)
            val characters = response.results.map { it.toCharacterDomain() }
            val nextKey = if (response.info.next.isNullOrBlank()) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = characters,
                prevKey = if (response.info.prev.isNullOrBlank()) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}