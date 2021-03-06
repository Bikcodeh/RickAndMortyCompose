package com.bikcode.rickandmortycompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bikcode.rickandmortycompose.data.local.RickAndMortyDatabase
import com.bikcode.rickandmortycompose.data.model.toEpisode
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Episode
import com.bikcode.rickandmortycompose.domain.repository.CharacterRepository
import com.bikcode.rickandmortycompose.domain.repository.LocalDataSource
import com.bikcode.rickandmortycompose.domain.repository.RemoteDataSource
import com.bikcode.rickandmortycompose.util.Resource
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val rickAndMortyDatabase: RickAndMortyDatabase,
    private val localDataSource: LocalDataSource
) : CharacterRepository {

    override fun getAllCharacters(): Flow<PagingData<Character>> {

        val characterSourceFactory = { localDataSource.getAllCharacters() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                rickAndMortyDatabase = rickAndMortyDatabase,
                remoteDataSource = remoteDataSource
            ),
            pagingSourceFactory = characterSourceFactory
        ).flow
    }

    override suspend fun getSelectedCharacter(characterId: Int): Character? {
        return localDataSource.getSelectedCharacter(characterId = characterId)
    }

    override fun searchCharacters(text: String): Flow<List<Character>> {
        return localDataSource.searchCharacters(text = "%$text%")
    }

    override suspend fun getEpisode(episodesUrl: List<String>): Flow<Resource<List<Episode>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val episodes = episodesUrl.asFlow().map { episode ->
                    remoteDataSource.getEpisode(episode)
                }.toCollection(mutableListOf())

                emit(Resource.Success(data = episodes.map { it.toEpisode() }))
                emit(Resource.Loading(false))

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load episodes", null))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load episodes", null))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Unexpected error", null))
            }
        }
    }

    override suspend fun getCharactersByStatus(status: String): List<Character> {
        return localDataSource.getCharactersByStatus(status = status)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}