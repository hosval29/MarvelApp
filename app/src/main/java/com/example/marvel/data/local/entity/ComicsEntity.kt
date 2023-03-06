package com.example.marvel.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvel.data.local.model.Character
import com.example.marvel.data.local.model.Creator
import com.example.marvel.data.local.model.Story
import com.example.marvel.data.local.model.Thumbnail
import kotlin.random.Random

@Entity(tableName = "comics_table")
data class ComicsEntity(
    val description: String = "",
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val modified: String = "",
    val rating: Double = Random.nextDouble(1.0, 5.0),
    @Embedded val thumbnail: Thumbnail = Thumbnail(),
    val title: String = "",
    val creators: List<Creator> = listOf(),
    val stories: List<Story> = listOf(),
    val characters: List<Character> = listOf(),
    @ColumnInfo(name = "page")
    var page: Int = 0
)