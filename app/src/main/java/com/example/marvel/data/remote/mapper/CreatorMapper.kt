package com.example.marvel.data.remote.mapper

import com.example.marvel.data.local.model.Creator
import com.example.marvel.data.remote.dto.CreatorDto
import com.example.marvel.domain.model.CreatorComic

fun CreatorDto.toCreatorComicEntity(): Creator {
    return Creator(
        name = fullName,
        imageUrl = "${thumbnail.path}.${thumbnail.extension}"
    )
}

fun Creator.toCreatorComic(): CreatorComic {
    return CreatorComic(
        name = name,
        imageUrl = imageUrl
    )
}