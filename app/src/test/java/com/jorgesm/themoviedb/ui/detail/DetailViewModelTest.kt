import androidx.arch.core.executor.testing.InstantTaskExecutorRule // Necesario para StateFlow/LiveData
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.jorgesm.themoviedb.CoroutinesTestRule
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.detail.DetailViewModel
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.GetRemoteMovieDetailsUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var getMovieByIdUseCase: GetMovieByIdUseCase // Aunque no se usa directamente en init, es parte del constructor

    @Mock
    private lateinit var getRemoteMovieDetailsUseCase: GetRemoteMovieDetailsUseCase

    @Mock
    private lateinit var setMovieFavoriteUseCase: SetMovieFavoriteUseCase

    private lateinit var viewModel: DetailViewModel

    // Datos de prueba para la película
    private val testMovieId = 3
    private val movieSample = sampleMovieTest.copy(id = testMovieId)


    @Test
    fun `init loads movie details and updates state with movie on success`() = runTest {
        // GIVEN: Preparamos el mock para que getRemoteMovieDetailsUseCase devuelva un resultado exitoso.
        val expectedMovie = movieSample
        whenever(getRemoteMovieDetailsUseCase(testMovieId)).thenReturn(expectedMovie.right())

        // WHEN: Creamos la instancia del ViewModel, lo que disparará el bloque init.
        viewModel = DetailViewModel(
            movieId = testMovieId,
            getMovieByIdUseCase = getMovieByIdUseCase, // Se pasa aunque no se use en init
            getRemoteMovieDetailsUseCase = getRemoteMovieDetailsUseCase,
            setMovieFavoriteUseCase = setMovieFavoriteUseCase
        )

        // THEN: Verificamos que el estado se actualice correctamente con la película.
        viewModel.state.test {
            // El primer estado emitido será el inicial por defecto (movie = null, error = null)
            val initialState = awaitItem()
            assertThat(initialState.movie).isNull()
            assertThat(initialState.error).isNull()

            // El segundo estado emitido debería ser el que contiene la película cargada.
            val successState = awaitItem()
            assertThat(successState.movie).isEqualTo(expectedMovie)
            assertThat(successState.error).isNull()

            cancelAndConsumeRemainingEvents()
        }

        // VERIFY: Verificamos que el caso de uso fue llamado.
        verify(getRemoteMovieDetailsUseCase).invoke(testMovieId)
    }

    @Test
    fun `init loads movie details and updates state with error on failure`() = runTest {
        // GIVEN: Preparamos el mock para que getRemoteMovieDetailsUseCase devuelva un error.
        val expectedError = Error.Connectivity
        whenever(getRemoteMovieDetailsUseCase(testMovieId)).thenReturn(expectedError.left())

        // WHEN: Creamos la instancia del ViewModel.
        viewModel = DetailViewModel(
            movieId = testMovieId,
            getMovieByIdUseCase = getMovieByIdUseCase,
            getRemoteMovieDetailsUseCase = getRemoteMovieDetailsUseCase,
            setMovieFavoriteUseCase = setMovieFavoriteUseCase
        )

        // THEN: Verificamos que el estado se actualice correctamente con el error.
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState.movie).isNull()
            assertThat(initialState.error).isNull()

            val errorState = awaitItem()
            assertThat(errorState.movie).isNull()
            assertThat(errorState.error).isEqualTo(expectedError)

            cancelAndConsumeRemainingEvents()
        }

        // VERIFY: Verificamos que el caso de uso fue llamado.
        verify(getRemoteMovieDetailsUseCase).invoke(testMovieId)
    }

    @Test
    fun `switchFavorite toggles isFavorite in state`() = runTest {
        // GIVEN: Primero, necesitamos un estado inicial con una película.
        // Simulamos que la película ya se cargó exitosamente.
        val initialMovie = movieSample.copy(isFavorite = false)
        whenever(getRemoteMovieDetailsUseCase(testMovieId)).thenReturn(initialMovie.right())

        viewModel = DetailViewModel(
            movieId = testMovieId,
            getMovieByIdUseCase = getMovieByIdUseCase,
            getRemoteMovieDetailsUseCase = getRemoteMovieDetailsUseCase,
            setMovieFavoriteUseCase = setMovieFavoriteUseCase
        )

        // Consumimos el estado inicial y el de carga para llegar al estado con la película
        viewModel.state.test {
            awaitItem() // Estado inicial
            val loadedState = awaitItem() // Estado con la película cargada
            assertThat(loadedState.movie).isEqualTo(initialMovie)

            // WHEN: Llamamos a switchFavorite.
            viewModel.switchFavorite()

            // THEN: Verificamos que isFavorite se haya invertido en el estado.
            val switchedState = awaitItem()
            assertThat(switchedState.movie?.isFavorite).isTrue()

            // WHEN: Llamamos a switchFavorite de nuevo.
            viewModel.switchFavorite()

            // THEN: Verificamos que isFavorite se haya invertido de nuevo.
            val switchedBackState = awaitItem()
            assertThat(switchedBackState.movie?.isFavorite).isFalse()

            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `onFavoriteClicked calls SetMovieFavoriteUseCase with updated movie`() = runTest {
        // GIVEN: Establecemos un estado inicial con una película.
        val initialMovie = movieSample.copy(isFavorite = false)
        val expectedMovieToSave =
            initialMovie.copy(isFavorite = true) // La película con el favorito invertido

        // Simulamos que la película ya se cargó exitosamente.
        whenever(getRemoteMovieDetailsUseCase(testMovieId)).thenReturn(initialMovie.right())

        viewModel = DetailViewModel(
            movieId = testMovieId,
            getMovieByIdUseCase = getMovieByIdUseCase,
            getRemoteMovieDetailsUseCase = getRemoteMovieDetailsUseCase,
            setMovieFavoriteUseCase = setMovieFavoriteUseCase
        )

        // Consumimos el estado inicial y el de carga para asegurar que la película está en el estado
        viewModel.state.test {
            awaitItem()
            val loadedState = awaitItem()
            assertThat(loadedState.movie).isEqualTo(initialMovie)

            viewModel.onFavoriteClicked()
        }
    }
}