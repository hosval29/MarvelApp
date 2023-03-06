package com.example.marvel.data.repository

import androidx.paging.*
import com.example.marvel.data.local.dao.ComicsDao
import com.example.marvel.data.local.dao.ComicsFavoritesDao
import com.example.marvel.data.local.model.Character
import com.example.marvel.data.local.model.Creator
import com.example.marvel.data.local.model.Story
import com.example.marvel.data.paging.ComicsRemoteMediator
import com.example.marvel.data.remote.api.ComicsApi
import com.example.marvel.data.remote.mapper.*
import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.model.ComicDetail
import com.example.marvel.domain.model.ComicFavorite
import com.example.marvel.domain.repository.ComicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ComicsRepositoryImpl(
    private val comicsDao: ComicsDao,
    private val comicsFavoritesDao: ComicsFavoritesDao,
    private val comicsRemoteMediator: ComicsRemoteMediator,
    private val comicsApi: ComicsApi
) : ComicsRepository {

    override fun getAllComics(): Flow<PagingData<Comic>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = NETWORK_PAGE_FETCH_DISTANCE,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            remoteMediator = comicsRemoteMediator,
            pagingSourceFactory = { comicsDao.getAllComics() }
        ).flow.map { pagingData -> pagingData.map { it.toComics() } }
    }

    override suspend fun getComicById(comicId: String): ComicDetail {
        withContext(Dispatchers.IO) {
            val responses = listOf(
                async { getCharacterComicById(comicId) },
                async { getCreatorComicById(comicId) },
                async { getStoriesComicById(comicId) }
            )

            val comicsEntity = async { comicsDao.getComicById(comicId) }
            val result = comicsEntity.await()

            comicsDao.updateComic(
                result.copy(
                    creators = responses.awaitAll()[0] as List<Creator>,
                    characters = responses.awaitAll()[1] as List<Character>,
                    stories = responses.awaitAll()[2] as List<Story>
                )
            )
        }

        return comicsDao.getComicById(comicId).toComicDetail()
    }

    private suspend fun getCharacterComicById(comicId: String): List<Character> {
        return try {
            val responseCharacters = comicsApi.getCharactersByComicId(comicId)
            responseCharacters.data.results.map { it.toCharacterComicEntity() }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getCreatorComicById(comicId: String): List<Creator> {
        return try {
            val responseCreators = comicsApi.getCreatorsByComicId(comicId)
            responseCreators.data.results.map { it.toCreatorComicEntity() }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getStoriesComicById(comicId: String): List<Story> {
        return try {
            val responseStories = comicsApi.getStoriesByComicId(comicId)
            responseStories.data.results.map { it.toStoryComicEntity() }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getAllComicsFavorites(): Flow<List<ComicFavorite>> {
        return comicsFavoritesDao.getAllComicsFavorites()
            .map { entities ->
                entities.map { it.toComicFavorite() }
            }
    }

    override suspend fun updateComicFavoriteById(isFavorite: Boolean, id: String) {
        comicsFavoritesDao.updateComicFavoriteById(
            isFavorite = isFavorite,
            id = id
        )
    }

    override suspend fun saveComicFavorite(comicFavorite: ComicFavorite) {
        comicsFavoritesDao.insertComicFavorite(comicFavorite.toComicsFavoritesEntity())
    }

    companion object {
        const val TAG = "ComicsRepositoryImpl"
        const val NETWORK_PAGE_SIZE = 30
        const val NETWORK_PAGE_FETCH_DISTANCE = 10
    }
}