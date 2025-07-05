package com.jorgesm.themoviedb.ui.common

import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error

data class UiState(
    val loading: Boolean = false,
    val movies: List<DomainMovie>? = null,
    val error: Error? = null
)