package com.jorgesm.themoviedb.ui.filter_by_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesm.themoviedb.ui.common.UiState
import com.jorgesm.themoviedb.usecases.RequestMovieByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterByNameViewModel @Inject constructor(
    private val requestMovieByNameUseCase: RequestMovieByNameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

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
}