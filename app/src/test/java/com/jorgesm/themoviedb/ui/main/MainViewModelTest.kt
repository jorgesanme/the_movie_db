package com.jorgesm.themoviedb.ui.main

import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.ui.main.MainViewModel
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()
    
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
        
        val result = mutableListOf<MainViewModel.UiState>()
        val job = launch { viewModel.state.toList(result) }
        runCurrent()
        
        job.cancel()
        
        assertEquals(MainViewModel.UiState(movies = movies), result[0])
    }
    
    @Test
    fun `Progress show when it is necessary`()= runTest {
        viewModel.onUiReady()
        val result = mutableListOf<MainViewModel.UiState>()
        val job = launch { viewModel.state.toList(result) }
        runCurrent()
        
        job.cancel()
        
        assertEquals(MainViewModel.UiState(movies = movies), result[0])
    }
    
    
}