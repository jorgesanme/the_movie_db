package com.jorgesm.themoviedb.usecases

import arrow.core.Either
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import javax.inject.Inject

class GetRemoteMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Either<Error, DomainMovie> =
        moviesRepository.getMovieDetails(movieId)
}