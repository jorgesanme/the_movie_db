package com.jorgesm.themoviedb.ui.filter_by_name

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.data.server.toError
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestMovieByNameUseCase
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
class FilterByNameViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val requestMovieByNameUseCase: RequestMovieByNameUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy( loading = false,error = cause.toError()) }}
                .collect{ movies -> _state.update {  UiState(loading = false, movies = movies) }
            }
        }
    }

    fun getMovieByName(movieName: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val result = requestMovieByNameUseCase(movieName)
            result.fold(
                ifLeft = { error ->
                        _state.value = _state.value.copy(
                        loading = false,
                        error = error,
                        movies = emptyList()
                    )
                },
               ifRight = { movies ->
                   Log.d("movie", "ByNameData=> ${movies} ")
                   val listMovie = movies.filterNotNull()
                   _state.value = _state.value.copy(
                       loading = false,
                       error = null,
                       movies = listMovie
                   )
               }
           )
        }
    }

    fun onUiReady(){
        getMovieByName("War")
        /*viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.value = _state.value.copy( loading = false, error = error)
        }*/
    }
    
    
    data class UiState(
        val loading: Boolean = false,
        val movies: List<DomainMovie>? = null,
        val error: Error? = null
    )
}