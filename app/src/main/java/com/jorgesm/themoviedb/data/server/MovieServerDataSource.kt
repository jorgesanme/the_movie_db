package com.jorgesm.themoviedb.data.server

import arrow.core.Either
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error

class MovieServerDataSource(
    private val apiKey: String
) : MovieRemoteDataSource {
    override suspend fun findPopularMovies(region: String): Either<Error, List<DomainMovie>> =
        tryCall {
            RemoteConnection.service
                .listPopularMovies(apiKey, region)
                .results.map { it.mapToDomainModel() }
        }
    
    private fun RemoteMovie.mapToDomainModel() = DomainMovie(
        id = this.id,
        title = this.title,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath ?: "",
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        voteAverage = this.voteAverage,
        isFavorite = false
    )
}