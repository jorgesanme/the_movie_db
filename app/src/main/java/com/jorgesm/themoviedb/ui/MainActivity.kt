package com.jorgesm.themoviedb.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jorgesm.themoviedb.Movie
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.RemoteConnection
import com.jorgesm.themoviedb.databinding.ActivityMainBinding
import com.jorgesm.themoviedb.utils.Constants
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import android.Manifest
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity() {
    
    private lateinit var mainBinding: ActivityMainBinding
    private var movies: List<Movie> = listOf()
    private var location: Location? = null
    
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            lifecycleScope.launch{
                location = getLocation(isGranted)
                /*val response = RemoteConnection.service.listPopularMovies(
                    getString(R.string.api_key),
                    getRegionFromLocation(location)
                )
                this@MainActivity.movies = response.results*/
            }
        }
   
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        
        
        
        lifecycleScope.launch {
            RemoteConnection.service.listPopularMovies(
                getString(R.string.api_key),
                getRegionFromLocation(location)
            ).results.let {
                movies = it
            }
            
            mainBinding.rvCovers.apply {
                adapter = MoviesAdapter(movies, listener ={ (onMovieClicked(it))} )
            }
        }

    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private suspend fun getLocation(granted: Boolean): Location? {
        return if (granted) findLastLocation() else null
    }
    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }
    private fun getRegionFromLocation(location: Location?): String {
        val geocoder = Geocoder(this@MainActivity)
        val fromLocation = location?.let {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        }
        return fromLocation?.firstOrNull()?.countryCode ?: "US"
    }
    
    private fun onMovieClicked(movie: Movie){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        startActivity(intent)
    }
    
}