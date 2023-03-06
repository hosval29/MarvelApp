package com.example.marvel.data.remote.dto

data class CreatorDto(
    val firstName: String = "",
    val fullName: String = "",
    val id: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val modified: String = "",
    val suffix: String = "",
    val thumbnail: Thumbnail = Thumbnail(),
) {
    data class Thumbnail(
        val extension: String = "",
        val path: String = ""
    )
}
