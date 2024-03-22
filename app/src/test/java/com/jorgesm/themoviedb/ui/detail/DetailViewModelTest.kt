package com.jorgesm.themoviedb.ui.detail

import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.detail.DetailViewModel.UiState
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    
    @get:Rule val coroutinesTestRule = CoroutinesTestRule()
    
    @Mock lateinit var getMovieByIdUseCase: GetMovieByIdUseCase
    @Mock lateinit var setMovieFavoriteUseCase: SetMovieFavoriteUseCase
    
    private lateinit var viewModel: DetailViewModel
    
    private val movie = sampleMovieTest.copy(3)
    
    @Before
    fun setUp(){
        whenever(getMovieByIdUseCase(3)).thenReturn(flowOf(movie))
        viewModel = DetailViewModel(3, getMovieByIdUseCase, setMovieFavoriteUseCase)
    }
    @Test
    fun `UI updated on start with the movie details`():Unit = runTest {
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = movie), awaitItem())
            cancel()
        }
    }
    @Test
    fun `OnFavoriteClick action call the useCase`():Unit = runTest{
        viewModel.onFavoriteClicked()
        runCurrent()
        
        verify(setMovieFavoriteUseCase).invoke(movie)
    }
}