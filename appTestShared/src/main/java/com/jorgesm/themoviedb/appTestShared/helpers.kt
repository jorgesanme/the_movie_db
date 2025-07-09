package com.jorgesm.themoviedb.appTestShared

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.data.database.MovieRoomDataSource
import com.jorgesm.themoviedb.data.server.MovieServerDataSource
import com.jorgesm.themoviedb.data.server.RemoteMovie
import com.jorgesm.themoviedb.data.server.RemoteMovieDetails
import com.jorgesm.themoviedb.ui.detail.DetailViewModel
import com.jorgesm.themoviedb.data.database.Movie as DatabaseMovie
import com.jorgesm.themoviedb.domain.DomainGenre
import com.jorgesm.themoviedb.domain.DomainGenres
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.GetRemoteMovieDetailsUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase

fun buildRepositoryWith(
    localData: List<DatabaseMovie>,
    remoteData: List<RemoteMovie>
): MoviesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)

    val localDataSource = MovieRoomDataSource(FakeMovieDao(localData))
    val remoteDataSource = MovieServerDataSource("1234", FakeRemoteService(remoteData))
    return MoviesRepository(regionRepository, localDataSource, remoteDataSource)

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

fun buildRemoteMovies(vararg id: Int, title: String = "Title") = id.map {
    RemoteMovie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title",
        overview = "Overview",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = title,
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}

fun RemoteMovie.toDomainMovie() =DomainMovie(
    id = this.id,
    title = this.title,
    releaseDate = this.releaseDate,
    posterPath = this.posterPath ?: "",
    backdropPath = this.backdropPath ?: "",
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    voteAverage = this.voteAverage,
    isFavorite = false
)

fun buildRemoteMovieDetails(id: Int) =
    RemoteMovieDetails(
        adult = false,
        backdropPath = "backdropPath_$id",
        belongsToCollection = "",
        budget = 0,
        genres = listOf(),
        homepage = "",
        id = id,
        imdbId = "",
        originCountry = listOf(),
        originalLanguage = "originalLanguage_$id",
        originalTitle = "originalTitl_$id",
        overview = "el overview para $id esta mucho parco",
        popularity = 0.0,
        posterPath = "posterPath_$id",
        productionCompanies = listOf(),
        productionCountries = listOf(),
        releaseDate = "",
        revenue = 0,
        runtime = 0,
        spokenLanguages = listOf(),
        status = "",
        tagline = "",
        title = "Title",
        video = true,
        voteAverage = 0.0,
        voteCount = id
    )

fun fakeDomainGenre(
    id: Int = 1,
    name: String = "Action"
) = DomainGenre(id = id, name = name)

fun fakeDomainGenreList(
      genres: List<DomainGenre> = listOf(
        fakeDomainGenre(1, "Action"),
        fakeDomainGenre(2, "Comedy"),
        fakeDomainGenre(3, "Drama")
    )
) = DomainGenres(genres)

fun buildViewModelForTest(
    movieId: Int,
    localData: List<DatabaseMovie> = emptyList(),
    remoteData: List<RemoteMovie> = emptyList(),
    ): DetailViewModel {
    val moviesRepository = buildRepositoryWith(localData, remoteData)

    val getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository)
    val setMovieFavoriteUseCase = SetMovieFavoriteUseCase(moviesRepository)
    val getRemoteMovieDetailsUseCase = GetRemoteMovieDetailsUseCase(moviesRepository)

    return DetailViewModel(
        movieId,
        getMovieByIdUseCase,
        getRemoteMovieDetailsUseCase,
        setMovieFavoriteUseCase
    )
}




