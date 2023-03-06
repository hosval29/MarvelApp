package com.example.marvel.data.remote.response

import com.example.marvel.data.remote.dto.SeriesDto

data class ResponseSeries(
    val attributionHTML: String = "",
    val attributionText: String = "",
    val code: String = "",
    val copyright: String = "",
    val `data`: Data = Data(),
    val etag: String = "",
    val status: String = ""
) {
    data class Data(
        val count: String = "",
        val limit: String = "",
        val offset: String = "",
        val results: List<SeriesDto> = listOf(),
        val total: String = ""
    )
}