package com.jorgesm.themoviedb.data

import arrow.core.Either
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import javax.inject.Inject
import com.jorgesm.themoviedb.domain.Error


class MoviesRepository @Inject constructor(
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

    suspend fun findMovieByName(movieName: String): Either<Error, List<DomainMovie>> {
        val region = regionRepository.findLasRegion()
        val result=  remoteDataSource.findMovieByName(region, movieName)
        return result
    }
    
    suspend fun switchFavorite(movie: DomainMovie): Error? {
            val updateMovie = movie.copy(isFavorite = !movie.isFavorite)
            return localDataSource.save(listOf(updateMovie))
        }
}
