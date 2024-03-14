package com.jorgesm.themoviedb.data.datasource

import arrow.core.Either
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error

interface MovieRemoteDataSource{
    suspend fun findPopularMovies( region: String): Either<Error, List<DomainMovie>>
    
}