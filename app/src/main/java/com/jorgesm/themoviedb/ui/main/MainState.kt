package com.jorgesm.themoviedb.ui.main


import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.jorgesm.themoviedb.model.Movie
import com.jorgesm.themoviedb.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ),
    navController: NavController = findNavController()
) = MainState(scope, locationPermissionRequester,navController)
class MainState(
    private val scope: CoroutineScope,
    private val locationPermissionRequester: PermissionRequester,
    private val navController: NavController
) {
    
    fun onMovieClicked(movie: Movie){
        val navAction = MainFragmentDirections.actionMainToDetail(movie)
        navController.navigate(navAction)
    }
    
    fun requestLocationPermission(afterRequest: (Boolean)-> Unit ) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }
}