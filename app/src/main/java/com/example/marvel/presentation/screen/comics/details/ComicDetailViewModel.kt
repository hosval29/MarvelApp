package com.example.marvel.presentation.screen.comics.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.domain.use_case.ComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val comicsUseCase: ComicsUseCase
) : ViewModel() {

    var state by mutableStateOf(ComicDetailState())
        private set

    fun getComicById(comicId: String) {
        viewModelScope.launch {
            val comicDetail = comicsUseCase.getComicById(comicId)
            state = state.copy(comicDetail = comicDetail)
        }
    }
}