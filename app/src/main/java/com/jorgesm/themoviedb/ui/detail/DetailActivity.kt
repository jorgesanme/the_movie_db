package com.jorgesm.themoviedb.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jorgesm.themoviedb.model.Movie
import com.jorgesm.themoviedb.databinding.ActivityDetailBinding
import com.jorgesm.themoviedb.utils.Constants
import com.jorgesm.themoviedb.utils.loadUrl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailActivity: AppCompatActivity() {
    
    
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.DetailViewModelFactory(
            requireNotNull(intent.getSerializableExtra(Constants.MOVIE) as Movie)) }
    
    private lateinit var binding: ActivityDetailBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        lifecycleScope.launch {
            // TODO: cambiar Create por Started
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect { updateUI(it.movie) }
            }
        }
        
    }
    
    private fun updateUI(movie: Movie) {
        val background = movie.backdropPath ?: movie.posterPath
        with(binding){
            movieDetailToolbar.title = movie.title
            detailImage.loadUrl(Constants.IMG_BASE_URL+Constants.IMG_780+background)
            movieDetailSummary.text = movie.overview
            movieDetailInfo.setMovieDetailText(movie)
        }
    }
}