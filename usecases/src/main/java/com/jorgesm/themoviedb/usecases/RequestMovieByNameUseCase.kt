package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import arrow.core.Either
import javax.inject.Inject

class RequestMovieByNameUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
     suspend operator fun invoke(movieName: String): Either<Error, List<DomainMovie>> =
           moviesRepository.findMovieByName(movieName = movieName )
}