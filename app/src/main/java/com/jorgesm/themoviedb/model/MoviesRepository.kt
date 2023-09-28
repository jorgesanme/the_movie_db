package com.jorgesm.themoviedb.model

import androidx.appcompat.app.AppCompatActivity
import com.jorgesm.themoviedb.R


class MoviesRepository(activity: AppCompatActivity) {

    private val apiKey =  activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)
    
    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(apiKey, regionRepository.findLasRegion())
}