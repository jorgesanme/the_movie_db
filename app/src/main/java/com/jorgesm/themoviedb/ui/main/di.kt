package com.jorgesm.themoviedb.ui.main

import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainFragmentModule{
    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)

}

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent{
    val mainViewModelFactory: MainViewModelFactory
}