package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.Error
import com.jorgesm.themoviedb.data.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {
        suspend operator fun invoke(): Error? = moviesRepository.requestPopularMovies()
}