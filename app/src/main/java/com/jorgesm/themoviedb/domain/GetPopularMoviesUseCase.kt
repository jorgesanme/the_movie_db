package com.jorgesm.themoviedb.domain

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.database.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.popularMovies
}