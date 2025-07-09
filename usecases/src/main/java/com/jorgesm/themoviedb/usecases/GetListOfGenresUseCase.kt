package com.jorgesm.themoviedb.usecases

import arrow.core.Either
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainGenres
import com.jorgesm.themoviedb.domain.Error
import javax.inject.Inject

class GetListOfGenresUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(): Either<Error, DomainGenres> =
        moviesRepository.getListOfGenres()
}