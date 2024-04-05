import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SetMovieFavoriteUseCaseTest {
    
    @Test
    fun `Invoke set movie as favorite useCase`():Unit = runBlocking {
        val movie = sampleMovieTest.copy(2)
        val moviesRepository = mock<MoviesRepository>()
        val setMovieFavoriteUseCase = SetMovieFavoriteUseCase(moviesRepository)
        
        setMovieFavoriteUseCase(movie)
        
        verify(moviesRepository).switchFavorite(movie)
        
    }
}