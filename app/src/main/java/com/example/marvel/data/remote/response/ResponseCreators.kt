package com.example.marvel.data.remote.response

import com.example.marvel.data.remote.dto.CreatorDto


data class ResponseCreators(
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
        val results: List<CreatorDto> = listOf(),
        val total: String = ""
    )
}