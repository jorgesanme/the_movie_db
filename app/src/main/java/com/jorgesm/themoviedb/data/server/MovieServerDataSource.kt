package com.jorgesm.themoviedb.data.server

import arrow.core.Either
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.di.ApiKey
import com.jorgesm.themoviedb.domain.DomainGenres
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
) : MovieRemoteDataSource {
    override suspend fun findPopularMovies(region: String): Either<Error, List<DomainMovie>> =
        tryCall {
            remoteService
                .listPopularMovies(apiKey, region)
                .results.map { it.mapToDomainModel() }
        }

    override suspend fun findMovieByName(
        region: String,
        movieName: String
    ): Either<Error, List<DomainMovie>> =
        tryCall {
            remoteService
                .getMovieByName(apiKey = apiKey, region = region, movieName = movieName)
                .results.filterNotNull().map { it.mapToDomainModel() }
        }

    override suspend fun getListOfGenres(region: String): Either<Error, DomainGenres> =
        tryCall {
            remoteService
                .getMovieGenres(apiKey = apiKey, region = region)
                .genres.mapToDomainGenres()
        }


}