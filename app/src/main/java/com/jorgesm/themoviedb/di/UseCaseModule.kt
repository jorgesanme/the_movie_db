package com.jorgesm.themoviedb.di

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {
    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        GetPopularMoviesUseCase(moviesRepository)
    @Provides
    fun provideGetMovieByIdUseCase(moviesRepository: MoviesRepository) =
        GetMovieByIdUseCase(moviesRepository)
    @Provides
    fun provideRequestPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        RequestPopularMoviesUseCase(moviesRepository)
    @Provides
    fun provideSetMovieFavoriteUseCase(moviesRepository: MoviesRepository) =
        SetMovieFavoriteUseCase(moviesRepository)
}