package com.jorgesm.themoviedb.data.datasource

import com.jorgesm.themoviedb.data.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String
){
    suspend fun findPopularMovies( region: String) =
        RemoteConnection.service
            .listPopularMovies(apiKey, region)
}