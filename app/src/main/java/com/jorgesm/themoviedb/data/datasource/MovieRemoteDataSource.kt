package com.jorgesm.themoviedb.data.datasource

import com.jorgesm.themoviedb.domain.DomainMovie

interface MovieRemoteDataSource{
    suspend fun findPopularMovies( region: String): List<DomainMovie>
    
}