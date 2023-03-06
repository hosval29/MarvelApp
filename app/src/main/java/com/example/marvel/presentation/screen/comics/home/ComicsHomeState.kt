package com.example.marvel.presentation.screen.comics.home

import com.example.marvel.domain.model.Comic

data class ComicsHomeState(
    val isLoading: Boolean = false,
    val comics: List<Comic> = emptyList()
)