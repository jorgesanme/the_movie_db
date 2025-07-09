package com.jorgesm.themoviedb.domain

data class DomainGenres(
    val genres: List<DomainGenre>
)

data class DomainGenre(
    val id: Int,
    val name: String
)