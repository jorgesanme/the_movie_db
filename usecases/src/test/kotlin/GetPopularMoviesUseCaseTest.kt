import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {
    @Test
    fun `Invoke call get popular movies useCase`():Unit = runBlocking {
        val movies = flowOf(listOf(sampleMovieTest.copy(1)))
        val getPopularMoviesUseCase = GetPopularMoviesUseCase( mock{
            on {popularMovies} doReturn movies
        })
        
        val result = getPopularMoviesUseCase()
        
        assertEquals(movies, result)
    }
}