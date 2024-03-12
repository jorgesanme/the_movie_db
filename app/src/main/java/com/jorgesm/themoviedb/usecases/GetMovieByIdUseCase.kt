package com.jorgesm.themoviedb.usecases

import com.jorgesm.themoviedb.data.MoviesRepository

class GetMovieByIdUseCase(private val repository: MoviesRepository) {
    
    operator fun invoke(id:Int) = repository.findMovieById(id)
}