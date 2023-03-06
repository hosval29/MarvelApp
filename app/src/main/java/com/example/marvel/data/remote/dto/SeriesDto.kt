package com.example.marvel.data.remote.dto

data class SeriesDto(
    val dates: List<Date> = listOf(),
    val description: String = "",
    val id: String = "",
    val modified: String = "",
    val pageCount: String = "",
    val resourceURI: String = "",
    val thumbnail: Thumbnail = Thumbnail(),
    val title: String = "",
    val variantDescription: String = ""
) {
    data class Date(
        val date: String = "",
        val type: String = ""
    )

    data class Thumbnail(
        val extension: String = "",
        val path: String = ""
    )
}
