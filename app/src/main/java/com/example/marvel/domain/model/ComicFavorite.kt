package com.example.marvel.domain.model

data class ComicFavorite(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val datePublished: String,
    val rating: Double,
    val isFavorite: Boolean = false
)
