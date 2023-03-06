package com.example.marvel.domain.model

data class ComicDetail(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val datePublished: String = "",
    val rating: Double = 0.0,
    val stories: List<StoryComic> = emptyList(),
    val creators: List<CreatorComic> = emptyList(),
    val characters: List<CharacterComic> = emptyList(),
    val isFavorite: Boolean = false
)
