package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<DomainMovie>> = repository.popularMovies
}