package com.jorgesm.themoviedb.domain

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.database.Movie

class SetMovieFavoriteUseCase(private val repository: MoviesRepository) {
    
    suspend operator fun invoke(movie: Movie) = repository.switchFavorite(movie)
}