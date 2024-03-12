package com.jorgesm.themoviedb.data

import com.jorgesm.themoviedb.App
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.framework.datasource.MovieRoomDataSource
import com.jorgesm.themoviedb.framework.datasource.MovieServerDataSource


class MoviesRepository(application: App) {
    
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieRoomDataSource(application.db.movieDao())
    private val remoteDataSource = MovieServerDataSource(application.getString(R.string.api_key))
    
    val popularMovies = localDataSource.movies
    
    fun findMovieById(id: Int) = localDataSource.findMovieById(id)
    suspend fun requestPopularMovies(): Error? = tryCall {
        if(localDataSource.isEmpty()){
            val movies: List<DomainMovie> = remoteDataSource.findPopularMovies(regionRepository.findLasRegion())
            localDataSource.save(movies)
        }
    }
    suspend fun switchFavorite(movie: DomainMovie) = tryCall {
        val updateMovie = movie.copy(isFavorite = !movie.isFavorite)
        localDataSource.save(listOf(updateMovie))
    }
}
