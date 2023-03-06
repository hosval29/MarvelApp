package com.example.marvel.domain.model

data class Comic(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val datePublished: String,
    val rating: Double
)
