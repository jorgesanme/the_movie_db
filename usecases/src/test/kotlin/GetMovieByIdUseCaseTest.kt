import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import org.junit.Assert.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@RunWith(MockitoJUnitRunner::class)
class GetMovieByIdUseCaseTest {
    
    @Test
    fun `Invoke call useCase to get movie by Id`():Unit = runBlocking{
        val movie = flowOf(sampleMovieTest.copy(id = 1))
        val getMovieByIdUseCase = GetMovieByIdUseCase(mock(){
            on {
                findMovieById(1)
            } doReturn (movie)
        })
        
        val result = getMovieByIdUseCase(1)
        
        assertEquals(movie, result)
        
    }
}