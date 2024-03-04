package com.jorgesm.themoviedb.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double
): Serializable
