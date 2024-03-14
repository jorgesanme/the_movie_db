package com.jorgesm.themoviedb.domain

import com.jorgesm.themoviedb.data.database.Movie

fun List<Movie>.toDomainModel(): List<DomainMovie> = map { it.toDomainModel() }
fun Movie.toDomainModel(): DomainMovie =
   DomainMovie(
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
      isFavorite = this.isFavorite
   )
fun List<DomainMovie>.toDBModel(): List<Movie> = map { it.toDBModel() }

fun DomainMovie.toDBModel(): Movie = Movie(
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
   isFavorite = this.isFavorite
)