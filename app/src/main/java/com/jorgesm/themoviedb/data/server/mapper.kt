package com.jorgesm.themoviedb.data.server

import com.jorgesm.themoviedb.domain.DomainGenre
import com.jorgesm.themoviedb.domain.DomainGenres
import com.jorgesm.themoviedb.domain.DomainMovie

fun RemoteMovie.mapToDomainModel() = DomainMovie(
    id = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterPath = this.posterPath ?: "",
    backdropPath = this.backdropPath ?: "",
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    voteAverage = this.voteAverage,
    isFavorite = false
)

fun List<Genre>.mapToDomainGenres() = DomainGenres(
    genres = this.map { it.mapToDomainGenre() }
)

fun Genre.mapToDomainGenre() = DomainGenre(
    id = this.id ,
    name = this.name
)

/*fun MovieDetails.mapToDomainModel() = DomainMovie(
    id = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterPath = this.posterPath ?: "",
    backdropPath = this.backdropPath ?: "",
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    voteAverage = this.voteAverage.toDouble(),
    isFavorite = false
)*/

fun RemoteMovieDetails.mapToDomain() = DomainMovie(
    id = this.id ?: 0,
    title = this.title ?: "",
    releaseDate = this.releaseDate,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    originalLanguage = this.originalLanguage ?: "",
    originalTitle = this.originalTitle ?: "" ,
    overview = this.overview?: "",
    popularity = this.popularity ?: 0.0,
    voteAverage = this.voteAverage,
    isFavorite = false
)