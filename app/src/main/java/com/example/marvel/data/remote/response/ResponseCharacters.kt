package com.example.marvel.data.remote.response


import com.example.marvel.data.remote.dto.CharacterDto

data class ResponseCharacters(
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
        val results: List<CharacterDto> = listOf(),
        val total: String = ""
    )
}