package com.jorgesm.themoviedb.data

import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.domain.tryCall
import com.jorgesm.themoviedb.framework.database.MovieRoomDataSource
import com.jorgesm.themoviedb.framework.server.MovieServerDataSource


class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieRoomDataSource,
    private val remoteDataSource: MovieServerDataSource
) {
    
    val popularMovies = localDataSource.movies
    
    fun findMovieById(id: Int) = localDataSource.findMovieById(id)
    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies: List<DomainMovie> =
                remoteDataSource.findPopularMovies(regionRepository.findLasRegion())
            localDataSource.save(movies)
        }
    }
    
    suspend fun switchFavorite(movie: DomainMovie) = tryCall {
        val updateMovie = movie.copy(isFavorite = !movie.isFavorite)
        localDataSource.save(listOf(updateMovie))
    }
}
