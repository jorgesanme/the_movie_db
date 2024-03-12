package com.jorgesm.themoviedb.domain


data class DomainMovie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
    val isFavorite: Boolean
)
