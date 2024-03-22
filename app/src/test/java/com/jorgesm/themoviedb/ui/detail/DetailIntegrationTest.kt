package com.jorgesm.themoviedb.ui.detail


import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.FakeLocalDataSource
import com.jorgesm.themoviedb.ui.FakeLocationDataSource
import com.jorgesm.themoviedb.ui.FakePermissionChecker
import com.jorgesm.themoviedb.ui.FakeRemoteDataSource
import com.jorgesm.themoviedb.ui.detail.DetailViewModel.UiState
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

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
        }
        cancel()
    }
    
    @Test
    fun `UI is updated with the movie on Start`():Unit = runTest {
        val viewModel = buildViewModelForTest(
            movieId = 3,
            localData = listOf(sampleMovieTest.copy(1), sampleMovieTest.copy(2))
        )
        
        viewModel.state.test{
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = sampleMovieTest.copy(3,) ), awaitItem())
            cancel()
        }
    }
    
    private fun buildViewModelForTest(
        movieId: Int,
        localData: List<DomainMovie> = emptyList(),
        remoteData: List<DomainMovie> = emptyList(),
    ): DetailViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)
        
        val localDataSource = FakeLocalDataSource()
        val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
        val moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
        
        val getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository)
        val setMovieFavoriteUseCase = SetMovieFavoriteUseCase(moviesRepository)
        
        return DetailViewModel(movieId, getMovieByIdUseCase, setMovieFavoriteUseCase)
    }
}