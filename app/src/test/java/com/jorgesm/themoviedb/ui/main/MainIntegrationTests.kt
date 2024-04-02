package com.jorgesm.themoviedb.ui.main

import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.testshared.sampleMovieTest
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
        val remoteData = listOf(sampleMovieTest.copy(1), sampleMovieTest.copy(2))
        val viewModel = buildViewModelForTest(remoteData = remoteData)
        
        viewModel.onUiReady()
        
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = emptyList()),awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(movies = remoteData, loading = false), awaitItem())
            cancel()
        }
    }
    
    @Test
    fun `Get data from local source when is available`(): Unit = runTest {
        val localData = listOf(sampleMovieTest.copy(8), sampleMovieTest.copy(9))
        val remoteData = listOf(sampleMovieTest.copy(1), sampleMovieTest.copy(2))
        val viewModel = buildViewModelForTest(localData, remoteData)
        
        viewModel.state.test{
            val uiState = UiState()
            val item = awaitItem()
            assertEquals(uiState, item)
            assertEquals(UiState(movies = localData), awaitItem())
            cancel()
        }
    }
    
    
    private fun buildViewModelForTest(
        localData: List<DomainMovie> = emptyList(),
        remoteData: List<DomainMovie> = emptyList(),
    ): MainViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}