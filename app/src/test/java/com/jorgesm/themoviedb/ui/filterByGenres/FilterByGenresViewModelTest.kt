package com.jorgesm.themoviedb.ui.filterByGenres

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.appTestShared.buildRemoteMovies
import com.jorgesm.themoviedb.appTestShared.fakeDomainGenreList
import com.jorgesm.themoviedb.appTestShared.toDomainMovie
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.ui.filter_by_genres.GenderViewModel
import com.jorgesm.themoviedb.usecases.GetFilteredMoviesByGenresUseCase
import com.jorgesm.themoviedb.usecases.GetListOfGenresUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FilterByGenresViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var getListOfGenresUseCase: GetListOfGenresUseCase

    @Mock
    private lateinit var getFilteredMoviesByGenresUseCase: GetFilteredMoviesByGenresUseCase

    private lateinit var viewModel: GenderViewModel

    // Datos de ejemplo
    private val sampleGenresList = fakeDomainGenreList()
    private val sampleMoviesList = buildRemoteMovies(1, 2, 3).map { it.toDomainMovie() }

    @Before
    fun setUp() {
        viewModel = GenderViewModel(getListOfGenresUseCase, getFilteredMoviesByGenresUseCase)
    }

    @Test
    fun `onUiReady calls getListOfGenresUseCase and updates genres StateFlow on success`() =
        runTest {
            // GIVEN: El caso de uso para obtener géneros devuelve una lista exitosamente.
            val genres = fakeDomainGenreList()
            whenever(getListOfGenresUseCase()).thenReturn(genres.right())

            // THEN: Verificamos las emisiones del StateFlow `genres`
            viewModel.genres.test {
                // Estado inicial (lista vacía)
                assertThat(awaitItem()).isEmpty()

                // WHEN: Se llama a onUiReady
                viewModel.onUiReady()

                // Estado con la lista de géneros cargada
                assertThat(awaitItem()).isEqualTo(sampleGenresList.genres)

                // VERIFY: Que el caso de uso fue llamado
                verify(getListOfGenresUseCase).invoke()
                cancelAndConsumeRemainingEvents()
            }

            // También verificamos que el _state principal no se ponga en error
            viewModel.state.test {
                assertThat(awaitItem().error).isNull()
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `onUiReady updates main state with error if getListOfGenresUseCase fails`() = runTest {
        // GIVEN: El caso de uso para obtener géneros devuelve un error.
        val expectedError = Error.Unknown("error")
        whenever(getListOfGenresUseCase()).thenReturn(expectedError.left())

        // THEN: Verificamos las emisiones del StateFlow `state`
        viewModel.state.test {
            // Estado inicial
            assertThat(awaitItem()).isEqualTo(GenderViewModel.UiState())

            // WHEN: Se llama a onUiReady
            viewModel.onUiReady()

            // Estado con el error
            val errorState = awaitItem()
            assertThat(errorState.loading).isFalse()
            assertThat(errorState.error).isEqualTo(expectedError)
            assertThat(errorState.movies).isEqualTo(emptyList<DomainMovie>())

            // VERIFY: Que el caso de uso fue llamado
            verify(getListOfGenresUseCase).invoke()
            cancelAndConsumeRemainingEvents()
        }

        // El StateFlow `genres` no debería cambiar si hay un error al cargar la lista
        viewModel.genres.test {
            assertThat(awaitItem()).isEmpty() // Permanece vacío
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `getListByGenre updates state with movies on successful filter`() = runTest {
        // GIVEN: El caso de uso para filtrar películas devuelve una lista exitosamente.
        val genreToFilter = "Action"
        whenever(getFilteredMoviesByGenresUseCase(genreToFilter)).thenReturn(sampleMoviesList.right())

        // THEN: Verificamos las emisiones del StateFlow `state`
        viewModel.state.test {
            // Estado inicial
            assertThat(awaitItem()).isEqualTo(GenderViewModel.UiState())

            // WHEN: Se llama a getListByGenre
            viewModel.getListByGenre(genreToFilter)

            // Estado de carga
            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()
            assertThat(loadingState.movies).isNull()
            assertThat(loadingState.error).isNull()

            // Estado con las películas filtradas
            val successState = awaitItem()
            assertThat(successState.loading).isFalse()
            assertThat(successState.movies).isEqualTo(sampleMoviesList)
            assertThat(successState.error).isNull()

            // VERIFY: Que el caso de uso fue llamado
            verify(getFilteredMoviesByGenresUseCase).invoke(genreToFilter)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getListByGenre updates state with error on filter failure`() = runTest {
        // GIVEN: El caso de uso para filtrar películas devuelve un error.
        val genreToFilter = "UnknownGenre"
        val expectedError = Error.Unknown("error")
        whenever(getFilteredMoviesByGenresUseCase(genreToFilter)).thenReturn(expectedError.left())

        // THEN: Verificamos las emisiones del StateFlow `state`
        viewModel.state.test {
            // Estado inicial
            assertThat(awaitItem()).isEqualTo(GenderViewModel.UiState())

            // WHEN: Se llama a getListByGenre
            viewModel.getListByGenre(genreToFilter)

            // Estado de carga
            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()

            // Estado con el error
            val errorState = awaitItem()
            assertThat(errorState.loading).isFalse()
            assertThat(errorState.movies).isNull()
            assertThat(errorState.error).isEqualTo(expectedError)

            // VERIFY: Que el caso de uso fue llamado
            verify(getFilteredMoviesByGenresUseCase).invoke(genreToFilter)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `initial state is correct`() = runTest {
        // GIVEN: ViewModel recién inicializado

        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(GenderViewModel.UiState())
            cancelAndConsumeRemainingEvents()
        }
        viewModel.genres.test {
            assertThat(awaitItem()).isEmpty()
            cancelAndConsumeRemainingEvents()
        }
    }
}