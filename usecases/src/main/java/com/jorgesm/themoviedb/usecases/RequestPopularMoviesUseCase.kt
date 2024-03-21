package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.Error
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
        suspend operator fun invoke(): Error? {
                return moviesRepository.requestPopularMovies()
        }
        
}

