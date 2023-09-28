package com.jorgesm.themoviedb.ui.main



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.jorgesm.themoviedb.databinding.ActivityMainBinding
import com.jorgesm.themoviedb.utils.Constants
import kotlinx.coroutines.launch
import com.jorgesm.themoviedb.model.MoviesRepository
import com.jorgesm.themoviedb.ui.detail.DetailActivity

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
            mainBinding.progressCircular.visibility = View.VISIBLE
            adapter.submitList( moviesRepository.findPopularMovies().results)
            mainBinding.progressCircular.visibility = View.GONE
        }
    }
}