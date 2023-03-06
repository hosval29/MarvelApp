package com.example.marvel.presentation.screen.comics.favorites

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
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val comicsUseCase: ComicsUseCase
) : ViewModel() {

    private var getAllComicsFavoritesJob: Job? = null

    var state by mutableStateOf(FavoriteState())
        private set

    init {
        refreshComicsFavorites()
    }

    private fun refreshComicsFavorites() {
        getAllComicsFavoritesJob?.cancel()
        getAllComicsFavoritesJob = comicsUseCase.getAllComicsFavorites()
            .onEach {
                state = state.copy(comicsFavorites = it)
            }.launchIn(viewModelScope)
    }
}