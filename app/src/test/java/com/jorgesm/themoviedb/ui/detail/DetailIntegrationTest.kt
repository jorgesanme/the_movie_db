package com.jorgesm.themoviedb.ui.detail


import app.cash.turbine.test
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.data.server.RemoteMovie
import com.jorgesm.themoviedb.data.database.Movie as DatabaseMovie
import com.jorgesm.themoviedb.ui.detail.DetailViewModel.UiState
import com.jorgesm.themoviedb.appTestShared.buildDatabaseMovies
import com.jorgesm.themoviedb.appTestShared.buildRepositoryWith
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
            localData = buildDatabaseMovies(1,2,3)
        )
        viewModel.onFavoriteClicked()
        
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(false , awaitItem().movie!!.isFavorite)
            assertEquals(true, awaitItem().movie!!.isFavorite)
            cancel()
        }
    }
    
    @Test
    fun `UI is updated with the movie on Start`():Unit = runTest {
        val viewModel = buildViewModelForTest(
            movieId = 2,
            localData = buildDatabaseMovies(1,2,3)
        )
        
        viewModel.state.test{
            assertEquals(UiState(), awaitItem())
            assertEquals(2, awaitItem().movie!!.id)
            cancel()
        }
    }
    
    private fun buildViewModelForTest(
        movieId: Int,
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList(),
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        
        val getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository)
        val setMovieFavoriteUseCase = SetMovieFavoriteUseCase(moviesRepository)
        
        return DetailViewModel(movieId, getMovieByIdUseCase, setMovieFavoriteUseCase)
    }
}