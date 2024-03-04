package com.jorgesm.themoviedb.model.datasource

import com.jorgesm.themoviedb.model.RegionRepository
import com.jorgesm.themoviedb.model.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String,
    private val regionRepository: RegionRepository
){
    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(apiKey, regionRepository.findLasRegion())
}