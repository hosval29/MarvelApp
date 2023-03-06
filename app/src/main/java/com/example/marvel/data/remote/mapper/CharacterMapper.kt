package com.example.marvel.data.remote.mapper

import com.example.marvel.data.remote.dto.CharacterDto
import com.example.marvel.domain.model.CharacterComic
import com.example.marvel.data.local.model.Character

fun CharacterDto.toCharacterComicEntity(): Character {
    return Character(
        name = name,
        imageUrl = "${thumbnail.path}.${thumbnail.extension}"
    )
}

fun Character.toCharacterComic(): CharacterComic {
    return CharacterComic(
        name = name,
        imageUrl = imageUrl
    )
}