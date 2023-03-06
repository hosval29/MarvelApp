package com.example.marvel.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.marvel.data.local.entity.ComicsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComics(comics: List<ComicsEntity>)

    @Query("SELECT * FROM comics_table ORDER BY page")
    fun getAllComics(): PagingSource<Int, ComicsEntity>

    @Query("DELETE FROM comics_table")
    suspend fun deleteAllComics()

    @Query("SELECT * FROM comics_table WHERE id = :id")
    suspend fun getComicById(id: String): ComicsEntity

    @Update
    suspend fun updateComic(comicsEntity: ComicsEntity)
}