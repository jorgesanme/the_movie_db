package com.jorgesm.themoviedb.model.datasource

import com.jorgesm.themoviedb.model.database.Movie
import com.jorgesm.themoviedb.model.database.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDao: MovieDao){
    
    val movies: Flow<List<Movie>> = movieDao.getAllMovies()
    fun isEmpty(): Boolean = movieDao.countMovies() == 0
    
    fun save(movies: List<Movie>) {
        movieDao.insertMovie(movies)
    }
}