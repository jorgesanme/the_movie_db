package com.jorgesm.themoviedb.appTestShared


import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.database.Movie
import com.jorgesm.themoviedb.data.database.MovieDao
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import com.jorgesm.themoviedb.data.server.RemoteMovie
import com.jorgesm.themoviedb.data.server.RemoteResult
import com.jorgesm.themoviedb.data.server.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow




class FakeMovieDao(movies: List<Movie> = emptyList()) : MovieDao {
    
    val inMemoryMovies = MutableStateFlow(movies)
    
    private lateinit var findMovieFlow: MutableStateFlow<Movie>
    override fun getAllMovies(): Flow<List<Movie>> = inMemoryMovies
    
    override fun findMovieById(id: Int): Flow<Movie> {
        findMovieFlow = MutableStateFlow(inMemoryMovies.value.first { it.id == id })
        return findMovieFlow
    }
    
    override suspend fun countMovies(): Int  = inMemoryMovies.value.size
    
    override suspend fun insertMovie(movies: List<Movie>) {
        inMemoryMovies.value = movies
        if(::findMovieFlow.isInitialized){
            movies.firstOrNull {it.id == findMovieFlow.value.id }
                ?.let { findMovieFlow.value = it }
        }
    }
    
}

class FakeRemoteService(
    private val movies: List<RemoteMovie> = emptyList()
): RemoteService{
    override suspend fun listPopularMovies(apiKey: String, region: String) = RemoteResult(
        1, movies, 1, movies.size
        )
    
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"
    
    override suspend fun findLastLocation(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true
    
    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}