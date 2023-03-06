package com.example.marvel.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.marvel.data.local.database.MarvelAppDatabase
import com.example.marvel.data.local.entity.ComicsEntity
import com.example.marvel.data.local.entity.RemoteKeyEntity
import com.example.marvel.data.remote.api.ComicsApi
import com.example.marvel.data.remote.mapper.toComicsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class ComicsRemoteMediator(
    private val database: MarvelAppDatabase,
    private val api: ComicsApi,
) : RemoteMediator<Int, ComicsEntity>() {

    companion object {
        const val TAG = "ComicsRemoteMediator"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ComicsEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val limit = state.config.pageSize
            val offset = page * 30
            val resultGetAllPokemon = api.getAllComics(limit = limit, offset = offset)
            val comics = resultGetAllPokemon.data.results.map { it.toComicsEntity() }
            val endOfPaginationReached = comics.isEmpty()
            withContext(Dispatchers.IO) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.remoteKeysDao.deleteAllRemoteKeys()
                        database.comicsDao.deleteAllComics()
                    }

                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remoteKeys = comics.map {
                        RemoteKeyEntity(
                            comicId = it.id,
                            prevKey = prevKey,
                            nextKey = nextKey,
                            currentPage = page
                        )
                    }
                    database.remoteKeysDao.insertAll(remoteKeys)
                    database.comicsDao.insertAllComics(
                        comics.onEachIndexed { _, comic ->
                            comic.page = page
                        }
                    )
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
    
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ComicsEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { comic ->
                database.remoteKeysDao.remoteKeysComicId(comic.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ComicsEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { comic ->
                database.remoteKeysDao.remoteKeysComicId(comic.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ComicsEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { comicId ->
                database.remoteKeysDao.remoteKeysComicId(comicId)
            }
        }
    }
}