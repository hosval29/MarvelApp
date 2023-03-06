package com.example.marvel.domain.use_case

data class ComicsUseCase(
    val getAllComics: GetAllComics,
    val getComicById: GetComicById,
    val getAllComicsFavorites: GetAllComicsFavorites,
    val updateComicFavoriteById: UpdateComicFavoriteById,
    val saveComicFavorite: SaveComicFavorite
)