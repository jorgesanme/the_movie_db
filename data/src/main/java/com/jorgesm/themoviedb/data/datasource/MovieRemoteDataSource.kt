package com.jorgesm.themoviedb.data.datasource

import arrow.core.Either
import com.jorgesm.themoviedb.domain.DomainGenres
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<DomainMovie>>
    suspend fun getMovieDetails(region: String, movieId: Int): Either<Error, DomainMovie>
    suspend fun findMovieByName(region: String, movieName: String): Either<Error, List<DomainMovie>>
    suspend fun filterMovieByGenres(region: String, genres: String): Either<Error, List<DomainMovie>>
    suspend fun getListOfGenres(region: String): Either<Error, DomainGenres>
}