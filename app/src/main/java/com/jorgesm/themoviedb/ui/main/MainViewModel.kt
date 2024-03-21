package com.jorgesm.themoviedb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.data.server.toError
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy( loading = false,error = cause.toError()) }}
                .collect{ movies -> _state.update {  UiState(loading = false, movies = movies) }
            }
        }
    }
    
    
    fun onUiReady(){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { it.copy( loading = false,error = error)}
        }
    }
    
    
    data class UiState(
        val loading: Boolean = false,
        val movies: List<com.jorgesm.themoviedb.domain.DomainMovie>? = null,
        val error: com.jorgesm.themoviedb.domain.Error? = null
    )
}