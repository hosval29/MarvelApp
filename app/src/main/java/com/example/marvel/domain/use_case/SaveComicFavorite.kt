package com.example.marvel.domain.use_case

import com.example.marvel.domain.model.ComicFavorite
import com.example.marvel.domain.repository.ComicsRepository

class SaveComicFavorite(
    private val repository: ComicsRepository
) {
    suspend operator fun invoke(comicFavorite: ComicFavorite) {
        repository.saveComicFavorite(comicFavorite)
    }
}