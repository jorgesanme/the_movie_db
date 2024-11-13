package com.jorgesm.themoviedb.composeUI.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jorgesm.themoviedb.composeUI.ui.screens.detail.Details
import com.jorgesm.themoviedb.composeUI.ui.screens.home.Home
import com.jorgesm.themoviedb.domain.DomainMovie


sealed class Screen(val route: String){
    data object Home: Screen("home")
    data object Detail: Screen("detail/{${NavArgs.ItemId.key}}"){
        fun createRoute(movie: DomainMovie) = "detail/${movie.id}"
    }
}

enum class NavArgs(val key: String){
    ItemId("itemId")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route){
            Home(onMovieClick = {navController.navigate(Screen.Detail.createRoute(it))})
        }
        composable(route=Screen.Detail.route,
            arguments = listOf(navArgument(NavArgs.ItemId.key) { type= NavType.IntType})
        ){
            Details(onBackClick = {navController.popBackStack()})
        }
    }
}