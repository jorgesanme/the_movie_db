package com.jorgesm.themoviedb.data

import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource


class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {
    
    val popularMovies = localDataSource.movies
    
    fun findMovieById(id: Int) = localDataSource.findMovieById(id)
    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLasRegion())
            movies.fold(ifLeft = {return it}){
                localDataSource.save(it)
            }
        }
        return null
    }
    
    suspend fun switchFavorite(movie: DomainMovie): Error?  {
            val updateMovie = movie.copy(isFavorite = !movie.isFavorite)
            return localDataSource.save(listOf(updateMovie))
        }
}
