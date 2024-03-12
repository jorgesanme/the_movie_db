package com.jorgesm.themoviedb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.data.database.Movie
import com.jorgesm.themoviedb.domain.GetMovieByIdUseCase
import com.jorgesm.themoviedb.domain.SetMovieFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    movieId: Int,
    getMovieByIdUseCase: GetMovieByIdUseCase,
    private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase
): ViewModel() {
    
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    
    init {
        viewModelScope.launch {
            getMovieByIdUseCase(movieId).collect{ movie ->
                _state.value = UiState(movie)
            }
        }
    }
    class UiState(val movie: Movie? = null )
    
    fun onFavoriteClicked() = viewModelScope.launch {
        _state.value.movie?.let {
            setMovieFavoriteUseCase(it)
        }
    }
    
    
    @Suppress("UNCHECKED_CAST")
    class DetailViewModelFactory(
        private val movieId: Int,
        private val getMovieByIdUseCase: GetMovieByIdUseCase,
        private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase
    ):
        ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(movieId, getMovieByIdUseCase, setMovieFavoriteUseCase ) as T
        }
    
    }
    
}