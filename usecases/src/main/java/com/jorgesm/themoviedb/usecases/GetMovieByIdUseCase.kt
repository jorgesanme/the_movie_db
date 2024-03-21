package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(private val repository: MoviesRepository) {
    
    operator fun invoke(id:Int): Flow<DomainMovie> = repository.findMovieById(id)
}