package com.jorgesm.themoviedb.ui


import arrow.core.right
import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import com.jorgesm.themoviedb.testshared.sampleMovieTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


val defaultFakeMovies = listOf(
    sampleMovieTest.copy(1),
    sampleMovieTest.copy(2),
    sampleMovieTest.copy(3),
    sampleMovieTest.copy(4)
)

class FakeLocalDataSource : MovieLocalDataSource {
    
    val inMemoryMovies = MutableStateFlow<List<DomainMovie>>(emptyList())
    
    private lateinit var findMovieFlow: MutableStateFlow<DomainMovie>
    override val movies = inMemoryMovies
    
    
    override suspend fun isEmpty() = movies.value.isEmpty()
    override fun findMovieById(id: Int): Flow<DomainMovie> {
        findMovieFlow = MutableStateFlow(inMemoryMovies.value.first { it.id == id })
        return findMovieFlow
    }
    
    override suspend fun save(movies: List<DomainMovie>): Error? {
        inMemoryMovies.value = movies
        if(::findMovieFlow.isInitialized){
            movies.firstOrNull {it.id == findMovieFlow.value.id }
                ?.let { findMovieFlow.value = it }
        }
        return null
    }
    
}

class FakeRemoteDataSource : MovieRemoteDataSource {
    
    var movies = defaultFakeMovies
    
    override suspend fun findPopularMovies(region: String) = movies.right()
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"
    
    override suspend fun findLastLocation(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true
    
    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}