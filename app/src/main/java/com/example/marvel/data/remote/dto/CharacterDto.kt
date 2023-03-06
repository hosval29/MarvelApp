package com.example.marvel.data.remote.dto


data class CharacterDto(
    val description: String = "",
    val id: String = "",
    val modified: String = "",
    val name: String = "",
    val resourceURI: String = "",
    val thumbnail: Thumbnail = Thumbnail()
) {
    data class Thumbnail(
        val extension: String = "",
        val path: String = ""
    )
}