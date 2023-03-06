package com.example.marvel.domain.use_case

import com.example.marvel.domain.model.ComicDetail
import com.example.marvel.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow

class GetComicById(
    private val repository: ComicsRepository
) {
    suspend operator fun invoke(comicId: String) : ComicDetail {
        return repository.getComicById(comicId)
    }
}