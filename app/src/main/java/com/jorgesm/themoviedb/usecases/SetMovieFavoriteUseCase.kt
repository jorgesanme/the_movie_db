package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie

class SetMovieFavoriteUseCase(private val repository: MoviesRepository) {
    
    suspend operator fun invoke(movie: DomainMovie) = repository.switchFavorite(movie)
}