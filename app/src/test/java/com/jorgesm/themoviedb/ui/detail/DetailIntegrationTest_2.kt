package com.jorgesm.themoviedb.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule // Necesaria para StateFlow
import app.cash.turbine.test
import arrow.core.Either
import com.google.common.truth.Truth.assertThat // Para aserciones más legibles
import com.jorgesm.themoviedb.CoroutinesTestRule // Tu regla de coroutines
import com.jorgesm.themoviedb.appTestShared.buildDatabaseMovies
import com.jorgesm.themoviedb.appTestShared.buildRemoteMovieDetails
import com.jorgesm.themoviedb.appTestShared.buildRemoteMovies
import com.jorgesm.themoviedb.appTestShared.buildRepositoryWith
import com.jorgesm.themoviedb.appTestShared.buildViewModelForTest
import com.jorgesm.themoviedb.data.server.RemoteMovie // Asumo que es tu modelo de red
import com.jorgesm.themoviedb.domain.DomainMovie // Importante para el estado del ViewModel
import com.jorgesm.themoviedb.domain.Error // Para manejar errores
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import com.jorgesm.themoviedb.ui.detail.DetailViewModel.UiState
import com.jorgesm.themoviedb.usecases.GetRemoteMovieDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTest_2 {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Para StateFlow

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule() // Tu regla

    // ID de película de prueba consistente
    private val testMovieId = 0

    // Película de ejemplo que esperamos obtener de la "red" o "base de datos" simulada
    // Asegúrate de que sampleMovieTest pueda ser adaptado o que tengas un DomainMovie de ejemplo
    // Es importante que el tipo coincida con lo que el ViewModel espera en su UiState.
    private val sampleDomainMovie = buildRemoteMovies(0)
    @Test
    fun `UI is updated with the movie details on Start when movie exists remotely`() = runTest {
        // GIVEN: Preparamos el repositorio para que devuelva una película específica cuando se le pida remotamente.
        // Asumimos que GetRemoteMovieDetailsUseCase es el que se llama en el init.

        val viewModel = buildViewModelForTest(
            movieId = testMovieId,
            // Simulamos que la película existe en la fuente remota
            localData = emptyList() ,// Asumimos que no está en la caché local inicialmente
            remoteData =  buildRemoteMovies(0)
        )

        // THEN: Verificamos que el estado se actualice con los detalles de la película.
        viewModel.state.test {
            // Estado inicial por defecto
            assertThat(awaitItem()).isEqualTo(UiState())

            // Estado después de la carga exitosa
            val loadedState = awaitItem()
            assertThat(loadedState.error).isNull()
            assertThat(loadedState.movie).isNotNull()
            // Comparamos los campos relevantes. Podrías comparar el objeto entero si tienes un `equals` bien definido.
            assertThat(loadedState.movie?.id).isEqualTo(testMovieId)
            assertThat(loadedState.movie?.title).isEqualTo(sampleDomainMovie[0].title)
            assertThat(loadedState.movie?.isFavorite).isFalse() // Estado inicial de favorito

            cancelAndConsumeRemainingEvents()
        }
    }

   /* @Test
    fun `onFavoriteClicked calls setMovieFavoriteUseCase with toggled value`() = runTest {
        val initialMovie = DomainMovie(
            id = 1,
            title = "Title",
            isFavorite = false,
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            originalLanguage = "en",
            originalTitle = "Title",
            overview = "Overview",
            popularity = 0.0,
            voteAverage = 0.0
        )

        var result: DomainMovie? = null

        val viewModel = buildViewModelForTest(
            movieId = testMovieId
        )


        // Esperamos a que el estado inicial esté disponible
        viewModel.state.test {
            awaitItem() // estado vacío
            awaitItem() // estado con película

            // WHEN
            viewModel.onFavoriteClicked()

            // THEN
            advanceUntilIdle() // Esperamos a que termine el launch

            assertThat(result).isNotNull()
            assertThat(result?.isFavorite).isTrue()

            cancelAndConsumeRemainingEvents()
        }
    }*/


    /*@Test
    fun `UI is updated with error on Start when movie does not exist remotely`() = runTest {
        // GIVEN: Preparamos el repositorio para que NO devuelva la película (lista remota vacía).

        val viewModel = buildViewModelForTest(
            movieId = testMovieId
        )

        // THEN: Verificamos que el estado se actualice con un error.
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(UiState())

            val errorState = awaitItem()
            assertThat(errorState.movie).isNull()
            assertThat(errorState.error).isNotNull()
            // Podrías ser más específico sobre el tipo de error si tu `GetRemoteMovieDetailsUseCase`
            // y `fold` lo manejan de forma diferenciada (ej. Error.NotFound)
            // Por ahora, solo verificamos que haya algún error.
            assertThat(errorState.error).isInstanceOf(Error.Unknown::class.java)


            cancelAndConsumeRemainingEvents()
        }
    }*/



   /*@Test
    fun `onFavoriteClicked updates favorite status in state and persists change`() = runTest {
        // GIVEN: Una película que inicialmente no es favorita.
        // El repositorio simulado debe poder "recordar" el cambio de favorito.
        // Tu `buildRepositoryWith` necesitaría manejar esto, o podrías necesitar una instancia
        // de un repositorio en memoria más sofisticado para este test.
        // Por simplicidad, asumiremos que `SetMovieFavoriteUseCase` actualiza la fuente de datos
        // y que `GetRemoteMovieDetailsUseCase` (o el que corresponda) reflejará ese cambio si se volviera a llamar.

        val initialRemoteMovie = buildRemoteMovies(0)

        val viewModel = buildViewModelForTest(
            movieId = testMovieId,
            remoteData = initialRemoteMovie,
            // Usaremos buildDatabaseMovies para simular la base de datos local
            // y verificar que el cambio se propaga allí si SetMovieFavoriteUseCase escribe en local.
            localData = buildDatabaseMovies(sampleDomainMovie.to copy(isFavorite = false)) // Inicialmente no favorita en local
        )
    }*/

   /* @Test
    fun `onFavoriteClicked updates favorite status in state and persists change`() = runTest {
        // GIVEN:
        // 1. Una película que inicialmente no es favorita en nuestra fuente de datos simulada.
        // 2. El ViewModel configurado para usar esta fuente de datos.

        // Estado inicial de la película (no favorita)
        val initialMovieState = sampleDomainMovie

        // Preparamos el repositorio.
        // `buildRepositoryWith` debe ser capaz de:
        //    a) Devolver `initialMovieState.toRemoteMovie()` cuando `GetRemoteMovieDetailsUseCase` lo pida.
        //    b) Permitir que `SetMovieFavoriteUseCase` modifique el estado de "favorito" de esta película.
        //    c) Devolver la película actualizada (favorita) si `GetRemoteMovieDetailsUseCase` se vuelve a llamar
        //       o si el flujo de datos del repositorio se actualiza y el ViewModel lo observa.
        //    Esta es la parte "integración": los casos de uso operan sobre un repositorio compartido.

        val viewModel  = buildViewModelForTest(
            0
        )

        }



        val viewModel = DetailViewModel(
            movieId = testMovieId,
            getMovieByIdUseCase = getMovieByIdUseCase,
            getRemoteMovieDetailsUseCase = getRemoteMovieDetailsUseCase,
            setMovieFavoriteUseCase = setMovieFavoriteUseCase
        )

        // THEN: Verificamos el flujo de estados
        viewModel.state.test {
            // 1. Estado inicial por defecto del UiState
            assertThat(awaitItem()).isEqualTo(UiState())

            // 2. Estado después de que init carga la película (no favorita)
            val loadedState = awaitItem()
            assertThat(loadedState.movie).isNotNull()
            assertThat(loadedState.movie?.isFavorite).isFalse()
            assertThat(loadedState.movie?.id).isEqualTo(testMovieId)

            // WHEN: El usuario hace clic en el botón de favorito en la UI
            // Esto debería:
            //  a) Llamar a `switchFavorite()` para actualizar la UI inmediatamente (optimista).
            //  b) Llamar a `onFavoriteClicked()` para persistir el cambio.
            // En tu ViewModel:
            //   - `switchFavorite()` actualiza el estado localmente.
            //   - `onFavoriteClicked()` llama al `setMovieFavoriteUseCase`.
            // Para esta prueba, simularemos ambas acciones como lo haría la UI.

            viewModel.switchFavorite() // Simula la actualización optimista de la UI

            // 3. Estado después de la actualización optimista (UI muestra como favorita)
            val optimisticFavoriteState = awaitItem()
            assertThat(optimisticFavoriteState.movie).isNotNull()
            assertThat(optimisticFavoriteState.movie?.isFavorite).isTrue()

            // Ahora persistimos el cambio
            viewModel.onFavoriteClicked()

            // `onFavoriteClicked` lanza una corrutina para `setMovieFavoriteUseCase`.
            // Necesitamos avanzar el dispatcher para asegurar que la corrutina se complete.
            advanceUntilIdle() // Asegura que las coroutines lanzadas por onFavoriteClicked completen

            // VERIFY PERSISTENCE (Opcional, pero bueno para integración):
            // Si quisiéramos verificar que el repositorio realmente se actualizó,
            // podríamos obtener la película de nuevo usando el `getMovieByIdUseCase` (o el que corresponda
            // para obtener datos frescos que reflejen la persistencia) y verificar su estado.
            // Esto depende de cómo `SetMovieFavoriteUseCase` y tu repositorio estén implementados.
            // Por ejemplo, si `SetMovieFavoriteUseCase` actualiza la base de datos y
            // `GetMovieByIdUseCase` lee de la base de datos:
            //
            // val persistedMovieFlow = getMovieByIdUseCase(testMovieId)
            // persistedMovieFlow.test {
            //     val movieFromDb = awaitItem() // Asumiendo que emite el item directamente
            //     assertThat(movieFromDb).isNotNull()
            //     assertThat(movieFromDb?.isFavorite).isTrue()
            //     cancelAndConsumeRemainingEvents()
            // }
            //
            // Sin embargo, el ViewModel actual no vuelve a cargar automáticamente el estado desde
            // el repositorio después de `onFavoriteClicked`. El estado ya fue actualizado
            // optimistamente por `switchFavorite`. Si `setMovieFavoriteUseCase` emitiera un nuevo
            // `DomainMovie` a través de un Flow que el ViewModel estuviera recolectando para
            // actualizar su estado, entonces podríamos esperar otra emisión aquí.
            // Dado el ViewModel actual, el estado ya refleja `isFavorite = true` por `switchFavorite`.

            // Si `SetMovieFavoriteUseCase` fallara y tuvieras lógica para revertir
            // el `switchFavorite`, necesitarías probar ese escenario también.

            // Por ahora, la prueba verifica que el estado de la UI se actualiza a favorito.
            // La "persistencia" se confía a que `setMovieFavoriteUseCase` funcione correctamente
            // con el `moviesRepository` simulado.

            // Si quieres probar el ciclo completo de des-favorito:
            viewModel.switchFavorite() // Usuario lo quita de favorito (UI)
            val optimisticUnFavoriteState = awaitItem()
            assertThat(optimisticUnFavoriteState.movie).isNotNull()
            assertThat(optimisticUnFavoriteState.movie?.isFavorite).isFalse()

            viewModel.onFavoriteClicked() // Persiste el cambio
            advanceUntilIdle()

            // Aquí podrías volver a verificar la persistencia si fuera necesario.cancelAndConsumeRemainingEvents() // Limpiar
        }
    }

    // La función buildViewModelForTest que estabas usando, adaptada:
    private fun buildViewModelForTest(
        movieId: Int,
        localData: List<MovieDb> = emptyList(), // Usar el tipo de tu base de datos
        remoteData: List<RemoteMovie> = emptyList(), // Usar el tipo de tu API
    ): DetailViewModel {
        // moviesRepository se crea dentro de cada test que lo necesite para un control más fino.
        // Si lo pasas aquí, asegúrate de que su estado es el esperado para la prueba específica.
        // Para esta estructura de prueba, es mejor crear el repositorio DENTRO de cada test
        // como se hizo en `onFavoriteClickedupdates favorite status in state and persists change`
        // para asegurar que `localData` y `remoteData` son específicas para ese escenario.

        // No obstante, si quieres mantenerla como la tenías:
        val moviesRepository = buildRepositoryWith(localData, remoteData)

        val getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository)
        val setMovieFavoriteUseCase = SetMovieFavoriteUseCase(moviesRepository)
        val getRemoteMovieDetailsUseCase = GetRemoteMovieDetailsUseCase(moviesRepository)

        return DetailViewModel(
            movieId,
            getMovieByIdUseCase,
            getRemoteMovieDetailsUseCase,
            setMovieFavoriteUseCase
        )
    }*/


}

