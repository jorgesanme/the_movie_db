package com.jorgesm.themoviedb.ui.main

import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.main.MainViewModel.UiState
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import org.junit.Assert.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule val coroutinesTestRule = CoroutinesTestRule()
    
    @Mock lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    @Mock lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    
    private lateinit var viewModel: MainViewModel
    private var movies = listOf(sampleMovieTest.copy(1))
    
    
    @Before
    fun setUp(){
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))
        viewModel = MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
    
    @Test
    fun `State is update with cached content immediately`()= runTest {
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = movies), awaitItem())
            cancel()
        }
    }
    
    @Test
    fun `Progress show when it is necessary`()= runTest {
        viewModel.onUiReady()
        
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = movies), awaitItem())
            assertEquals(UiState(movies = movies, loading = true), awaitItem())
            assertEquals(UiState(movies = movies, loading = false), awaitItem())
            cancel()
        }
    }
}