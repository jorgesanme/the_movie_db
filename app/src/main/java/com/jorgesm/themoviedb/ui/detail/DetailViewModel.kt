package com.jorgesm.themoviedb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.di.MovieId
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.GetRemoteMovieDetailsUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getRemoteMovieDetailsUseCase: GetRemoteMovieDetailsUseCase,
    private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
       initMovieDetails()
    }
    private fun initMovieDetails(){
        viewModelScope.launch {
            getRemoteMovieDetailsUseCase(movieId).fold(ifLeft = { error ->
                _state.update { UiState(error = error, movie = null) }
            }, ifRight = { movie ->
                _state.update { UiState(movie = movie) }
            })
        }
    }


    fun switchFavorite() {
        val current = _state.value
        val newIsFavorite = current.movie?.isFavorite
        newIsFavorite?.let { it
        val updatedMovie = current.movie.copy(isFavorite = !it)
        _state.value = current.copy(movie = updatedMovie)
        }
    }


    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let {
                setMovieFavoriteUseCase(it.copy(isFavorite = !it.isFavorite))
            }
        }
    }


    data class UiState(
        val error: Error? = null,
        val movie: DomainMovie? = null
    )

}