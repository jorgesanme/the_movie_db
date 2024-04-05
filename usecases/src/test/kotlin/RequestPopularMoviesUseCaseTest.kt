import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RequestPopularMoviesUseCaseTest {
    @Test
    fun `Invoke calls request server popular movies useCase`():Unit = runBlocking {
        val moviesRepository = mock<MoviesRepository>()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        
        requestPopularMoviesUseCase()
        
        verify(moviesRepository).requestPopularMovies()
    }
}