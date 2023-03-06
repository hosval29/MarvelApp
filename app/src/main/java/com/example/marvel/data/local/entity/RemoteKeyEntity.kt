package com.example.marvel.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_table")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "comic_id")
    val comicId: String,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?
)