package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.Error

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {
        suspend operator fun invoke(): Error? {
                return moviesRepository.requestPopularMovies()
        }
        
}

