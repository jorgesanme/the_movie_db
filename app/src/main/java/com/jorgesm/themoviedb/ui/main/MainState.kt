package com.jorgesm.themoviedb.ui.main


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.data.Error
import com.jorgesm.themoviedb.data.database.Movie
import com.jorgesm.themoviedb.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ),
    navController: NavController = findNavController(),
    context: Context = requireContext()
) = MainState(context, scope, locationPermissionRequester, navController)
class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val locationPermissionRequester: PermissionRequester,
    private val navController: NavController
) {
    
    fun onMovieClicked(movie: Movie){
        val navAction = MainFragmentDirections.actionMainToDetail(movie.id)
        navController.navigate(navAction)
    }
    
    fun requestLocationPermission(afterRequest: (Boolean)-> Unit ) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }
    fun errorToString(error: Error) = when(error){
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}