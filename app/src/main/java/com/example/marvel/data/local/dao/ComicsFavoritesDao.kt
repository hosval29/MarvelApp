package com.example.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel.data.local.entity.ComicsEntity
import com.example.marvel.data.local.entity.ComicsFavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicsFavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComicFavorite(comicsFavoritesEntity: ComicsFavoritesEntity)

    @Query("SELECT * FROM comics_favorites_table WHERE isFavorite = 1")
    fun getAllComicsFavorites(): Flow<List<ComicsFavoritesEntity>>

    @Query("UPDATE comics_favorites_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateComicFavoriteById(isFavorite: Boolean, id: String)
}