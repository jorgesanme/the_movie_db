package com.jorgesm.themoviedb.ui.filterByName

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test // Para probar StateFlow fácilmente
import com.google.common.truth.Truth.assertThat // Para aserciones legibles
import arrow.core.left // Para simular Either.Left
import arrow.core.right // Para simular Either.Right
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.appTestShared.buildRemoteMovies
import com.jorgesm.themoviedb.appTestShared.toDomainMovie
import com.jorgesm.themoviedb.domain.DomainMovie // Asegúrate de importar tu clase DomainMovie
import com.jorgesm.themoviedb.domain.Error // Asegúrate de importar tu clase Error
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.common.UiState // Importa tu UiState común
import com.jorgesm.themoviedb.ui.filter_by_name.FilterByNameViewModel
import com.jorgesm.themoviedb.usecases.RequestMovieByNameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FilterByNameViewModelTest {

    // Regla para ejecutar tareas de Arquitectura de Componentes de forma síncrona
    // Necesaria para probar StateFlow de forma síncrona.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Regla para manejar Dispatchers en pruebas de coroutines
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    // Mock para el caso de uso
    @Mock
    private lateinit var requestMovieByNameUseCase: RequestMovieByNameUseCase

    // ViewModel que se va a probar
    private lateinit var viewModel: FilterByNameViewModel

    @Before
    fun setUp() {
        // Inicializa el ViewModel con el mock del caso de uso
        viewModel = FilterByNameViewModel(requestMovieByNameUseCase)
    }

    @Test
    fun `getMovieByName updates state with movies on successful search`() = runTest {
        // GIVEN: Preparamos el mock para que devuelva una lista de películas exitosamente.
        val listOfId = listOf(0,1,2,3)
        val movieNameQuery = "Inception"
        val expectedMovies = buildRemoteMovies(*listOfId.toIntArray(), title = "Inceptio").map { it.toDomainMovie() }
        // Simulamos que el caso de uso devuelve un resultado exitoso (Either.Right)
        whenever(requestMovieByNameUseCase(movieNameQuery)).thenReturn(expectedMovies.right())

        // THEN: Verificamos las emisiones del StateFlow
        viewModel.state.test {
            // 1. Estado inicial por defecto
            assertThat(awaitItem()).isEqualTo(UiState())

            // WHEN: Llamamos a la función para obtener películas por nombre
            viewModel.getMovieByName(movieNameQuery)

            // 2. Estado de carga (loading = true)
            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()
            assertThat(loadingState.movies).isNull()
            assertThat(loadingState.error).isNull()

            // 3. Estado con las películas encontradas (loading = false, movies = expectedMovies)
            val successState = awaitItem()
            assertThat(successState.loading).isFalse()
            assertThat(successState.movies).isEqualTo(expectedMovies)
            assertThat(successState.error).isNull()

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getMovieByName updates state with error on search failure`() = runTest {
        // GIVEN: Preparamos el mock para que devuelva un error.
        val noData = emptyList<DomainMovie>()
        val movieNameQuery = "UnknownMovie123"
        val expectedError = Error.Connectivity
        // Simulamos que el caso de uso devuelve un error (Either.Left)
        whenever(requestMovieByNameUseCase(movieNameQuery)).thenReturn(expectedError.left())

        // THEN: Verificamos las emisiones del StateFlow
        viewModel.state.test {
            // 1. Estado inicial por defecto
            assertThat(awaitItem()).isEqualTo(UiState())

        // WHEN: Llamamos a la función para obtener películas por nombre
            viewModel.getMovieByName(movieNameQuery)
            // 2. Estado de carga (loading = true)
            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()


            // 3. Estado con el error (loading = false, error = expectedError, movies = emptyList)
            val errorState = awaitItem()
            assertThat(errorState.loading).isFalse()
            assertThat(errorState.movies).isEqualTo(noData)
            assertThat(errorState.error).isEqualTo(expectedError)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getMovieByName filters out null movies from result`() = runTest {
        // GIVEN: El caso de uso devuelve una lista que contiene nulos.
        val movieNameQuery = "MixedList"
        val movie1 = sampleMovieTest.copy(id = 1, title = "Movie 1")
        val movie2 = sampleMovieTest.copy(id = 2, title = "Movie 2")
        val moviesWithNulls: List<DomainMovie?> = listOf(movie1, null, movie2, null)
        val expectedFilteredMovies = listOf(movie1, movie2)

        whenever(requestMovieByNameUseCase(movieNameQuery)).thenReturn(moviesWithNulls.filterNotNull().right())

        // THEN:
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(UiState()) // Initial

            viewModel.getMovieByName(movieNameQuery)

            assertThat(awaitItem().loading).isTrue() // Loading

            val successState = awaitItem()
            assertThat(successState.loading).isFalse()
            assertThat(successState.movies).isEqualTo(expectedFilteredMovies)
            assertThat(successState.error).isNull()

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `initial state is correct`() = runTest {
        // GIVEN: ViewModel recién inicializado (en setUp)
        // THEN: Verificamos que el estado inicial sea el esperado
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(UiState(loading = false, movies = null, error = null))
            cancelAndConsumeRemainingEvents()
        }
    }
}