package com.example.marvel.presentation.screen.comics.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.remote.mapper.toComicFavorite
import com.example.marvel.domain.use_case.ComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsHomeViewModel @Inject constructor(
    private val comicsUseCase: ComicsUseCase
) : ViewModel() {

    val dataComics = comicsUseCase.getAllComics()

    fun onEvent(event: ComicsHomeEvent) {
        when(event) {
            is ComicsHomeEvent.SetComicFavorite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    comicsUseCase.saveComicFavorite(
                        comicFavorite = event.comic
                            .toComicFavorite()
                            .copy(isFavorite = true)
                    )
                }
            }
        }
    }
}