package com.example.marvel.data.remote.mapper

import com.example.marvel.data.local.model.Story
import com.example.marvel.data.remote.dto.StoriesDto
import com.example.marvel.domain.model.StoryComic

fun StoriesDto.toStoryComicEntity(): Story {
    return Story(
        name = title,
        imageUrl = if (thumbnail != null) "${thumbnail.path}.${thumbnail.extension}" else null
    )
}

fun Story.toStoryComic() : StoryComic {
    return StoryComic(
        name = name,
        imageUrl = imageUrl
    )
}