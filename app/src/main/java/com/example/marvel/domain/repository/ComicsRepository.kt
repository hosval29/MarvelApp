package com.example.marvel.domain.repository

import androidx.paging.PagingData
import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.model.ComicDetail
import com.example.marvel.domain.model.ComicFavorite
import com.example.marvel.presentation.screen.comics.favorites.FavoriteState
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getAllComics(): Flow<PagingData<Comic>>

    suspend fun getComicById(comicId: String) : ComicDetail

    fun getAllComicsFavorites() : Flow<List<ComicFavorite>>

    suspend fun updateComicFavoriteById(isFavorite: Boolean, id: String)

    suspend fun saveComicFavorite(comicFavorite: ComicFavorite)
}