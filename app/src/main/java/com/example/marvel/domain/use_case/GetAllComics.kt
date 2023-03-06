package com.example.marvel.domain.use_case

import androidx.paging.PagingData
import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow

class GetAllComics(private val repository: ComicsRepository) {
    operator fun invoke(): Flow<PagingData<Comic>> =
        repository.getAllComics()
}