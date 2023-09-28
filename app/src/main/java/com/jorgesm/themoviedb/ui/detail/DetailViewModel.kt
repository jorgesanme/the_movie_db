package com.jorgesm.themoviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jorgesm.themoviedb.model.Movie

class DetailViewModel(movie: Movie): ViewModel() {
    
    private val _state = MutableLiveData(UiState(movie))
    val state: LiveData<UiState> get() = _state
    
    class UiState(val movie: Movie)
    
    
    
    @Suppress("UNCHECKED_CAST")
    class DetailViewModelFactory(private val movie: Movie): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(movie) as T
        }
    
    }
    
}