package com.example.marvel.presentation.screen.comics.home

import com.example.marvel.domain.model.Comic

sealed class ComicsHomeEvent {
    data class SetComicFavorite(
        val comic: Comic
    ) : ComicsHomeEvent()
}
