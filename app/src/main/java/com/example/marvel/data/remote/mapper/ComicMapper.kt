package com.example.marvel.data.remote.mapper

import com.example.marvel.data.local.entity.ComicsEntity
import com.example.marvel.data.local.model.Thumbnail
import com.example.marvel.data.remote.dto.ComicDto
import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.model.ComicDetail
import com.example.marvel.domain.model.ComicFavorite

fun ComicDto.toComicsEntity(): ComicsEntity {
    return ComicsEntity(
        id = id,
        title = title,
        description = description ?: "",
        modified = modified,
        thumbnail = Thumbnail(
            extension = thumbnail.extension,
            path = thumbnail.path
        )
    )
}

fun ComicsEntity.toComics(): Comic {
    return Comic(
        id = id,
        title = title,
        description = description,
        rating = rating,
        image = "${thumbnail.path}.${thumbnail.extension}",
        datePublished = ""
    )
}

fun ComicsEntity.toComicDetail() : ComicDetail {
    return ComicDetail(
        id = id,
        title = title,
        description = description,
        rating = rating,
        image = "${thumbnail.path}.${thumbnail.extension}",
        datePublished = "",
        stories = stories.map { it.toStoryComic() },
        creators = creators.map { it.toCreatorComic() },
        characters = characters.map { it.toCharacterComic() }
    )
}

fun Comic.toComicFavorite() : ComicFavorite {
    return ComicFavorite(
        id = id,
        title = title,
        description = description,
        rating = rating,
        image = image,
        datePublished = datePublished
    )
}