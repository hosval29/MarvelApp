package com.example.marvel.presentation.screen.comics.favorites

import com.example.marvel.domain.model.ComicFavorite

data class FavoriteState(
    val comicsFavorites: List<ComicFavorite> = emptyList()
)
