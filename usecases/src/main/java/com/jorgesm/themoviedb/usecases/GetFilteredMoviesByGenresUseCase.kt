package com.jorgesm.themoviedb.usecases

import arrow.core.Either
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import javax.inject.Inject

class GetFilteredMoviesByGenresUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(genres: String): Either<Error, List<DomainMovie>> =
        moviesRepository.getFilteredMoviesByGenres(genres)
}