package com.jorgesm.themoviedb.ui.main



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jorgesm.themoviedb.databinding.ActivityMainBinding
import com.jorgesm.themoviedb.model.Movie
import com.jorgesm.themoviedb.utils.Constants
import com.jorgesm.themoviedb.model.MoviesRepository
import com.jorgesm.themoviedb.ui.detail.DetailActivity
import com.jorgesm.themoviedb.utils.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var mainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(MoviesRepository(this)) }
    private val adapter = MoviesAdapter{ viewModel.onMovieClicked(it) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setupObservable()
        mainBinding.rvCovers.adapter = adapter
    }
    
    private fun setupObservable() {
        lifecycleScope.launch {
            // TODO: cambiar Create por Started 
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect(::updateUI)
            }
        }
    }
    
    private fun updateUI(state: MainViewModel.UiState) {
        mainBinding.progressCircular.visible = state.loading
        state.movies.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }
    
    private fun navigateTo(movie: Movie){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        startActivity(intent)
    }
}
