package com.example.marvel.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvel.data.local.dao.ComicsDao
import com.example.marvel.data.local.dao.ComicsFavoritesDao
import com.example.marvel.data.local.dao.RemoteKeysDao
import com.example.marvel.data.local.entity.ComicsEntity
import com.example.marvel.data.local.entity.ComicsFavoritesEntity
import com.example.marvel.data.local.entity.RemoteKeyEntity

@Database(
    entities = [ComicsEntity::class, RemoteKeyEntity::class, ComicsFavoritesEntity::class],
    version = 1
)
@TypeConverters(MarvelDatabaseConverters::class)
abstract class MarvelAppDatabase : RoomDatabase() {
    abstract val comicsDao: ComicsDao
    abstract val comicsFavoritesDao: ComicsFavoritesDao
    abstract val remoteKeysDao: RemoteKeysDao
}