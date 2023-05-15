package com.jorgesm.themoviedb


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jorgesm.themoviedb.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        mainBinding.button.setOnClickListener{
            getData()
        }
    }

    private fun getData(){
        lifecycleScope.launch {
            val remoteMovies = RemoteConnection.service.listPopularMovies(getString(R.string.api_key))
            toast(remoteMovies.results.size.toString())
        }
    }
}