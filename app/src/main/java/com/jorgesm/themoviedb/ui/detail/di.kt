package com.jorgesm.themoviedb.ui.detail

import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailFragmentModule(private val movieId: Int){
    @Provides
    fun provideDetailViewModelFactory(
        getMovieByIdUseCase: GetMovieByIdUseCase,
        setMovieFavoriteUseCase: SetMovieFavoriteUseCase
    ) = DetailViewModelFactory(movieId, getMovieByIdUseCase, setMovieFavoriteUseCase)
}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent{
    val detailViewModelFactory: DetailViewModelFactory
}