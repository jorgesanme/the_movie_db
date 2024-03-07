package com.jorgesm.themoviedb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.model.Error
import com.jorgesm.themoviedb.model.MoviesRepository
import com.jorgesm.themoviedb.model.database.Movie
import com.jorgesm.themoviedb.model.toError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            moviesRepository.popularMovies
                .catch { cause -> _state.update { it.copy(error = cause.toError()) }}
                .collect{ movies -> _state.update {  UiState(movies = movies) }
            }
        }
    }
    
    
    fun onUiReady(){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = moviesRepository.requestPopularMovies()
            _state.update { it.copy( loading = false,error = error)}
        }
    }
    
    
    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  MainViewModel(moviesRepository) as T
    }
    
}