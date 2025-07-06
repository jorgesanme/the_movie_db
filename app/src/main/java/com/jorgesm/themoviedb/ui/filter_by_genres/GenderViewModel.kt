package com.jorgesm.themoviedb.ui.filter_by_genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.domain.DomainGenre
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.usecases.GetFilteredMoviesByGenresUseCase
import com.jorgesm.themoviedb.usecases.GetListOfGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val getListOfGenresUseCase: GetListOfGenresUseCase,
    private val getFilteredMoviesByGenresUseCase: GetFilteredMoviesByGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _genres = MutableStateFlow(listOf<DomainGenre>())
    val genres: StateFlow<List<DomainGenre>> = _genres.asStateFlow()


    fun getListByGenre(genre: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            getFilteredMoviesByGenresUseCase(genre)
                .fold(
                    ifLeft = { error ->

                        _state.update {
                            it.copy(
                                loading = false,
                                error = error,
                                movies = null
                            )
                        }
                    },
                    ifRight = { movies ->
                        _state.update { UiState(loading = false, movies = movies) }
                    })
        }
    }


    private fun getListOfGenres() {
        viewModelScope.launch {
            val result = getListOfGenresUseCase()
            result.fold(
                ifLeft = {},
                ifRight = { genres ->
                    _genres.value = genres.genres
                }
            )
        }
    }


    fun onUiReady() {
        getListOfGenres()
    }


    data class UiState(
        val loading: Boolean = false,
        val movies: List<DomainMovie>? = null,
        val error: Error? = null
    )
}