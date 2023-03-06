package com.example.marvel.data.remote.mapper

import com.example.marvel.data.local.entity.ComicsFavoritesEntity
import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.model.ComicFavorite

fun ComicsFavoritesEntity.toComicFavorite(): ComicFavorite {
    return ComicFavorite(
        id = id,
        title = title,
        description = description,
        rating = rating,
        image = image,
        datePublished = "",
        isFavorite = isFavorite
    )
}

fun ComicFavorite.toComicsFavoritesEntity(): ComicsFavoritesEntity {
    return ComicsFavoritesEntity(
        id = id,
        title = title,
        description = description,
        rating = rating,
        image = image,
        isFavorite = isFavorite
    )
}

fun ComicFavorite.toComic() : Comic {
    return Comic(
        id = id,
        title = title,
        description = description,
        rating = rating,
        image = image,
        datePublished = datePublished
    )
}
