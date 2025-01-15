package com.jorgesm.themoviedb.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.jorgesm.themoviedb.composeUI.ui.screens.NavArgs
import com.jorgesm.themoviedb.di.MovieId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {
    @Provides
    @ViewModelScoped
    @MovieId
    fun provideMovieId(savedStateHandle: SavedStateHandle): Int {
        val movieId = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).movieId
        if (movieId> -1) return movieId
        return savedStateHandle[NavArgs.ItemId.key] ?: -1
    }
}