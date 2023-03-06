package com.example.marvel.data.remote.response

import com.example.marvel.data.remote.dto.ComicDto


data class ResponseComics(
    val code: Int = 0,
    val `data`: Data = Data(),
    val etag: String = "",
    val status: String = ""
) {
    data class Data(
        val count: Int = 0,
        val limit: Int = 0,
        val offset: Int = 0,
        val results: List<ComicDto> = listOf(),
        val total: Int = 0
    )
}