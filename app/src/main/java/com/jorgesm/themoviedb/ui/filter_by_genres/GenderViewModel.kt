package com.jorgesm.themoviedb.ui.filter_by_genres

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.data.server.toError
import com.jorgesm.themoviedb.domain.DomainGenre
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.usecases.GetListOfGenresUseCase
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestMovieByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val getListOfGenresUseCase: GetListOfGenresUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestMovieByNameUseCase: RequestMovieByNameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _genres = MutableStateFlow(listOf<DomainGenre>())
    val genres: StateFlow<List<DomainGenre>> = _genres.asStateFlow()

    init {
        /*viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy( loading = false,error = cause.toError()) }}
                .collect{ movies -> _state.update {  UiState(loading = false, movies = movies) }
            }
        }*/
        getListOfGenres()
    }

    fun getListByGenre() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            getPopularMoviesUseCase()
                .catch { cause ->
                    _state.update {
                        it.copy(
                            loading = false,
                            error = cause.toError()
                        )
                    }
                }
                .collect { movies ->
                    _state.update { UiState(loading = false, movies = movies) }
                }
        }
    }


    private fun getListOfGenres() {
        viewModelScope.launch {

            val result = getListOfGenresUseCase()

            result.fold(
                ifLeft = { error ->
//                    _state.update { UiState(loading = false, movies = null, error= error ) }
                },
                ifRight = { genres ->
                    Log.d("genres", "Genres ->$genres")
                    _genres.value = genres.genres

                }
            )
        }
    }

    /*fun getMovieByName(movieName: String) {
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
    }*/

    fun onUiReady() {
//        getMovieByName("War")
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