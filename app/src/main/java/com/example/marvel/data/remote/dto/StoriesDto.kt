package com.example.marvel.data.remote.dto

data class StoriesDto(
    val description: String? = null,
    val id: String = "",
    val modified: String = "",
    val resourceURI: String = "",
    val thumbnail: Thumbnail? = null,
    val title: String = ""
) {
    data class Thumbnail(
        val extension: String = "",
        val path: String = ""
    )
}