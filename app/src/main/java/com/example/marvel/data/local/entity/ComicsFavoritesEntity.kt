package com.example.marvel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvel.data.local.model.Character
import com.example.marvel.data.local.model.Creator
import com.example.marvel.data.local.model.Story
import kotlin.random.Random

@Entity(tableName = "comics_favorites_table")
data class ComicsFavoritesEntity(
    val description: String = "",
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val rating: Double = Random.nextDouble(1.0, 5.0),
    val image: String = "",
    val title: String = "",
    val creators: List<Creator> = listOf(),
    val stories: List<Story> = listOf(),
    val characters: List<Character> = listOf(),
    val isFavorite: Boolean = false
)