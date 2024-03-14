package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke() = repository.popularMovies
}