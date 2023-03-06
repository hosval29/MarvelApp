package com.example.marvel.domain.use_case

import com.example.marvel.domain.model.ComicFavorite
import com.example.marvel.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow

class GetAllComicsFavorites(
    private val repository: ComicsRepository
) {
    operator fun invoke(): Flow<List<ComicFavorite>> {
        return repository.getAllComicsFavorites()
    }
}