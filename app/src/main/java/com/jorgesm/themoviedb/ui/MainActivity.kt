package com.jorgesm.themoviedb.ui



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jorgesm.themoviedb.databinding.ActivityMainBinding
import com.jorgesm.themoviedb.utils.Constants
import kotlinx.coroutines.launch
import com.jorgesm.themoviedb.model.MoviesRepository

class MainActivity : AppCompatActivity() {
    
    private lateinit var mainBinding: ActivityMainBinding
    
    private val moviesRepository by lazy { MoviesRepository(this) }
    
    private val adapter = MoviesAdapter{
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.MOVIE, it)
        startActivity(intent)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        mainBinding.rvCovers.adapter = adapter
        lifecycleScope.launch{
            adapter.submitList( moviesRepository.findPopularMovies().results)
        }
    }
}