package com.jorgesm.themoviedb.ui.detail


import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.detail.DetailViewModel.UiState
import com.jorgesm.themoviedb.ui.main.buildRepositoryWith
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class DetailIntegrationTest {
    
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()
    @Test
    fun `Set favorite updated in local data`(): Unit = runTest {
        val viewModel = buildViewModelForTest(
            movieId = 2,
            localData = listOf(sampleMovieTest.copy(1), sampleMovieTest.copy(2))
        )
        viewModel.onFavoriteClicked()
        
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = sampleMovieTest.copy(2, isFavorite = false) ), awaitItem())
            assertEquals(UiState(movie = sampleMovieTest.copy(2, isFavorite = true) ), awaitItem())
            cancel()
        }
    }
    
    @Test
    fun `UI is updated with the movie on Start`():Unit = runTest {
        val viewModel = buildViewModelForTest(
            movieId = 2,
            localData = listOf(sampleMovieTest.copy(1), sampleMovieTest.copy(2))
        )
        
        viewModel.state.test{
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = sampleMovieTest.copy(2,) ), awaitItem())
            cancel()
        }
    }
    
    private fun buildViewModelForTest(
        movieId: Int,
        localData: List<DomainMovie> = emptyList(),
        remoteData: List<DomainMovie> = emptyList(),
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        
        val getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository)
        val setMovieFavoriteUseCase = SetMovieFavoriteUseCase(moviesRepository)
        
        return DetailViewModel(movieId, getMovieByIdUseCase, setMovieFavoriteUseCase)
    }
}