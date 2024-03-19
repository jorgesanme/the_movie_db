package com.jorgesm.themoviedb.di

import com.jorgesm.themoviedb.ui.detail.DetailViewModelFactory
import com.jorgesm.themoviedb.ui.main.MainViewModelFactory
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object ViewModelsModule {
    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    
    @Provides
    fun provideDetailViewModelFactory(
        getMovieByIdUseCase: GetMovieByIdUseCase,
        setMovieFavoriteUseCase: SetMovieFavoriteUseCase
    ) = DetailViewModelFactory(-1, getMovieByIdUseCase, setMovieFavoriteUseCase)
}