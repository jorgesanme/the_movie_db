package com.jorgesm.themoviedb.model

import com.jorgesm.themoviedb.App
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.model.database.Movie
import com.jorgesm.themoviedb.model.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.model.datasource.MovieRemoteDataSource


class MoviesRepository(application: App) {
    
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(application.getString(R.string.api_key))
    
    val popularMovies = localDataSource.movies
    suspend fun requestPopularMovies() {
        if(localDataSource.isEmpty()){
            val movies: RemoteResult = remoteDataSource.findPopularMovies(regionRepository.findLasRegion())
            localDataSource.save(movies.results.map { it.toLocalDataMovie() })
        }
    }
}


private fun RemoteMovie.toLocalDataMovie() = Movie(
        id = this.id,
        title = this.title,
        releaseDate = this.releaseDate,
        posterPath =this.posterPath,
        backdropPath = this.backdropPath,
        originalLanguage =this.originalLanguage,
        originalTitle =this.originalTitle,
        overview = this.overview,
        popularity =this.popularity,
        voteAverage = this.voteAverage
)