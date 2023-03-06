package com.example.marvel.data.local.database

import androidx.room.TypeConverter
import com.example.marvel.data.local.model.Character
import com.example.marvel.data.local.model.Creator
import com.example.marvel.data.local.model.Story
import com.google.gson.Gson

class MarvelDatabaseConverters {
    @TypeConverter
    fun fromStoriesToJsonString(stories: List<Story>): String = Gson().toJson(stories)

    @TypeConverter
    fun fromJsonStringToStories(moves: String): List<Story> =
        Gson().fromJson(moves, Array<Story>::class.java).toList()

    @TypeConverter
    fun fromCreatorToJsonString(creators: List<Creator>): String =
        Gson().toJson(creators)

    @TypeConverter
    fun fromJsonStringToCreators(creators: String): List<Creator> =
        Gson().fromJson(creators, Array<Creator>::class.java).toList()

    @TypeConverter
    fun fromCharacterToJsonString(characters: List<Character>): String =
        Gson().toJson(characters)

    @TypeConverter
    fun fromJsonStringToCharacter(characters: String): List<Character> =
        Gson().fromJson(characters, Array<Character>::class.java).toList()
}