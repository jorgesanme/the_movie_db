import arrow.core.right
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {
    
    @Mock lateinit var localDataSource: MovieLocalDataSource
    @Mock lateinit var remoteDataSource: MovieRemoteDataSource
    @Mock lateinit var regionRepository: RegionRepository
    
    lateinit var moviesRepository: MoviesRepository
    
    private val localMovies = flowOf(listOf(sampleMovie.copy(id = 1)))
    @Before
    fun setUp(){
        whenever(localDataSource.movies).thenReturn(localMovies)
        moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }
    
    @Test
    fun `Popular movies are taken from local data base if available`(): Unit = runBlocking{
        
        val result = moviesRepository.popularMovies
        
        assertEquals(localMovies, result)
    }
    @Test
    fun `Popular movies are saved to local database on start`(): Unit = runBlocking {
        val remoteMovies = listOf(sampleMovie.copy(2))
        
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLasRegion()).thenReturn(RegionRepository.DEFAULT_REGION)
//        whenever(remoteDataSource.findPopularMovies(RegionRepository.DEFAULT_REGION)).thenReturn(remoteMovies.right())
        whenever(remoteDataSource.findPopularMovies(any())).thenReturn(remoteMovies.right())
        
        moviesRepository.requestPopularMovies()
        
        verify(localDataSource).save(remoteMovies)
    }
    
    @Test
    fun `Find a movie by id in local database`(): Unit = runBlocking {
        val movie = flowOf(sampleMovie.copy(id = 3))
        whenever(localDataSource.findMovieById(3)).thenReturn(movie)
        
        val result = moviesRepository.findMovieById(3)
        
        assertEquals(movie,result)
    }
    
    @Test
    fun `Set movie to favorite`():Unit = runBlocking {
        val movie = sampleMovie.copy(isFavorite = false)
        
        moviesRepository.switchFavorite(movie)
        
        verify(localDataSource).save(argThat { get(0).isFavorite })
    }
    
    @Test
    fun `Set movie to no favorite`():Unit = runBlocking {
        val movie = sampleMovie.copy(isFavorite = true)
        
        moviesRepository.switchFavorite(movie)
        
        verify(localDataSource).save(argThat { !get(0).isFavorite })
    }
}

private val sampleMovie = DomainMovie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false
)