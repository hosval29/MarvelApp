package com.example.marvel.data.remote.api

import com.example.marvel.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicsApi {

    @GET("comics")
    suspend fun getAllComics(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ResponseComics

    @GET("comics/{comicId}/characters")
    suspend fun getCharactersByComicId(
        @Path("comicId") comicId: String
    ): ResponseCharacters

    @GET("comics/{comicId}/creators")
    suspend fun getCreatorsByComicId(
        @Path("comicId") comicId: String
    ): ResponseCreators

    @GET("comics/{comicId}/stories")
    suspend fun getStoriesByComicId(
        @Path("comicId") comicId: String
    ): ResponseStories

}