package com.jorgesm.themoviedb.ui.main

import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.appTestShared.buildDatabaseMovies
import com.jorgesm.themoviedb.appTestShared.buildRemoteMovies
import com.jorgesm.themoviedb.appTestShared.buildRepositoryWith
import com.jorgesm.themoviedb.data.database.Movie as DatabaseMovie
import com.jorgesm.themoviedb.data.server.RemoteMovie
import com.jorgesm.themoviedb.ui.main.MainViewModel.UiState
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*


@ExperimentalCoroutinesApi
class MainIntegrationTests {
    
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()
    
    
    @Test
    fun `Data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteMovies(4,5,6)
        val viewModel = buildViewModelForTest(
            localData = emptyList(),
            remoteData = remoteData)
        
        viewModel.onUiReady()
        
        viewModel.state.test {
            assertEquals(UiState(movies = null),awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())
            val uiState = awaitItem()
            val movies = uiState.movies!!
            assertEquals("Title 4", movies[0].title)
            assertEquals("Title 5", movies[1].title)
            assertEquals("Title 6", movies[2].title)
            cancel()
        }
    }
    
    @Test
    fun `Get data from local source when is available`(): Unit = runTest {
        val localData = buildDatabaseMovies(1,2,3)
        val remoteData = buildRemoteMovies(4,5,6)
        val viewModel = buildViewModelForTest(localData, remoteData)
        
        viewModel.state.test{
            assertEquals(UiState(movies = null),awaitItem())
            
            val movies = awaitItem().movies!!
            assertEquals("Title 1", movies[0].title)
            assertEquals("Title 2", movies[1].title)
            assertEquals("Title 3", movies[2].title)
            cancel()
        }
    }
    
    
    private fun buildViewModelForTest(
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList(),
    ): MainViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}