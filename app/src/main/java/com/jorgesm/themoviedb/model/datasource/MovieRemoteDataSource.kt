package com.jorgesm.themoviedb.model.datasource

import com.jorgesm.themoviedb.model.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String,
    
){
    suspend fun findPopularMovies( region: String) =
        RemoteConnection.service
            .listPopularMovies(apiKey, region)
}