package com.jorgesm.themoviedb.data.datasource

import com.jorgesm.themoviedb.data.RemoteConnection
import com.jorgesm.themoviedb.data.RemoteMovie
import com.jorgesm.themoviedb.domain.DomainMovie

class MovieRemoteDataSource(
    private val apiKey: String
){
    suspend fun findPopularMovies( region: String) =
        RemoteConnection
            .service
            .listPopularMovies(apiKey, region)
            .results.map { it.mapToDomainModel()}
}

private fun RemoteMovie.mapToDomainModel() = DomainMovie(
    id = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath ?: "",
    originalLanguage =this.originalLanguage,
    originalTitle =this.originalTitle,
    overview = this.overview,
    popularity =this.popularity,
    voteAverage = this.voteAverage,
    isFavorite = false
)
