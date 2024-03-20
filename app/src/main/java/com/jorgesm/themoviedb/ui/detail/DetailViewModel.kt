package com.jorgesm.themoviedb.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieByIdUseCase: GetMovieByIdUseCase,
    private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase
): ViewModel() {
    
    private val movieId = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).movieId
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    
    init {
        viewModelScope.launch {
            getMovieByIdUseCase(movieId).collect{ movie ->
                _state.value = UiState(movie)
            }
        }
    }
    
    class UiState(val movie: com.jorgesm.themoviedb.domain.DomainMovie? = null )
    
    fun onFavoriteClicked() = viewModelScope.launch {
        _state.value.movie?.let {
            setMovieFavoriteUseCase(it)
        }
    }
}