package com.jorgesm.themoviedb.data.datasource

import com.jorgesm.themoviedb.data.database.MovieDao
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.toDBModel
import com.jorgesm.themoviedb.domain.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalDataSource(private val movieDao: MovieDao){
    
    val movies: Flow<List<DomainMovie>> = movieDao.getAllMovies().map { it.toDomainModel() }
    suspend fun isEmpty(): Boolean =  movieDao.countMovies() == 0
    
    fun findMovieById(id: Int): Flow<DomainMovie> = movieDao.findMovieById(id).map { it.toDomainModel() }
    
    suspend fun save(movies: List<DomainMovie>) {
        movieDao.insertMovie(movies.toDBModel())
    }
}