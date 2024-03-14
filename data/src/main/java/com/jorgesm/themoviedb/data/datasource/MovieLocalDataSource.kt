package com.jorgesm.themoviedb.data.datasource

import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.domain.Error
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<DomainMovie>>
    
    suspend fun isEmpty(): Boolean
    fun findMovieById(id: Int): Flow<DomainMovie>
    
    suspend fun save(movies: List<DomainMovie>): Error?
}