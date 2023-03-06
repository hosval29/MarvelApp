package com.example.marvel.domain.use_case

import com.example.marvel.domain.repository.ComicsRepository

class UpdateComicFavoriteById(
    private val repository: ComicsRepository
) {
    suspend operator fun invoke(
        isFavorite: Boolean,
        id: String
    ) {
        repository.updateComicFavoriteById(
            isFavorite = isFavorite,
            id = id
        )
    }
}
