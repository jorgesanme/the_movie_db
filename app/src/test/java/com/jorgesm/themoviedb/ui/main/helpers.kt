package com.jorgesm.themoviedb.ui.main

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.data.database.Movie as DatabaseMovie
import com.jorgesm.themoviedb.data.database.MovieRoomDataSource
import com.jorgesm.themoviedb.data.server.MovieServerDataSource
import com.jorgesm.themoviedb.data.server.RemoteMovie
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.ui.FakeMovieDao
import com.jorgesm.themoviedb.ui.FakeLocationDataSource
import com.jorgesm.themoviedb.ui.FakePermissionChecker
import com.jorgesm.themoviedb.ui.FakeRemoteService

fun buildRepositoryWith(
    localData: List<DatabaseMovie>,
    remoteData: List<RemoteMovie>
): MoviesRepository{
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    
    val localDataSource = MovieRoomDataSource(FakeMovieDao(localData))
    val remoteDataSource = MovieServerDataSource("1234", FakeRemoteService(remoteData))
    return  MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    
}

fun buildDatabaseMovies(vararg id: Int) = id.map {
    DatabaseMovie(
        id = it,
        title = "Title $it",
        overview = "Overview $it",
        releaseDate = "01/01/2025",
        posterPath = "",
        backdropPath = "",
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        popularity = 5.0,
        voteAverage = 5.1,
        isFavorite = false
    )
}

fun buildRemoteMovies(vararg id: Int) = id.map {
    RemoteMovie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}