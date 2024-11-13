package com.jorgesm.themoviedb.composeUI.ui.screens.home

import android.Manifest
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.composeUI.ui.screens.MovieTopAppBar
import com.jorgesm.themoviedb.composeUI.ui.screens.Screen
import com.jorgesm.themoviedb.composeUI.ui.screens.common.ErrorText
import com.jorgesm.themoviedb.composeUI.ui.screens.common.Loading
import com.jorgesm.themoviedb.composeUI.ui.screens.common.PermissionRequestEffect
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.ui.main.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onMovieClick: (DomainMovie) -> Unit,
    vm: MainViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) {
        vm.onUiReady()
    }

    Screen {
        Scaffold(
            topBar = {
                MovieTopAppBar(
                    title = stringResource(id = R.string.title_activity_main),
                    scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { paddingValues ->
            if (state.loading)
                Loading(modifier = Modifier.padding(paddingValues))

            state.error?.let { ErrorText(it) }

            state.movies?.let {
                MoviesGrid(
                    movies = it,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}