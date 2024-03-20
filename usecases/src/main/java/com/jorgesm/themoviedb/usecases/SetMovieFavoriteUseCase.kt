package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import javax.inject.Inject

class SetMovieFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {
    
    suspend operator fun invoke(movie: DomainMovie) = repository.switchFavorite(movie)
}