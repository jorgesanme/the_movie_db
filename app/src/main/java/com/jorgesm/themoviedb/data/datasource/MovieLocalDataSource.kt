package com.jorgesm.themoviedb.data.datasource

import com.jorgesm.themoviedb.data.database.Movie
import com.jorgesm.themoviedb.data.database.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDao: MovieDao){
    
    val movies: Flow<List<Movie>> = movieDao.getAllMovies()
    suspend fun isEmpty(): Boolean =  movieDao.countMovies() == 0
    
    fun findMovieById(id: Int): Flow<Movie> = movieDao.findMovieById(id)
    
    suspend fun save(movies: List<Movie>) {
        movieDao.insertMovie(movies)
    }
}