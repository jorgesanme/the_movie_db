package com.jorgesm.themoviedb.data.database

import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.server.tryCall
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.domain.toDBModel
import com.jorgesm.themoviedb.domain.toDomainModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {
    
    override val movies: Flow<List<DomainMovie>> = movieDao.getAllMovies().map { it.toDomainModel() }
    override suspend fun isEmpty(): Boolean =  movieDao.countMovies() == 0
    
    override fun findMovieById(id: Int): Flow<DomainMovie> = movieDao.findMovieById(id).map { it.toDomainModel() }
    
    override suspend fun save(movies: List<DomainMovie>): Error? = tryCall {
        movieDao.insertMovie(movies.toDBModel())
    }.fold(
        ifLeft = {it},
        ifRight = {null}
    )
}