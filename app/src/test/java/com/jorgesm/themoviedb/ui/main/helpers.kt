package com.jorgesm.themoviedb.ui.main

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.ui.FakeLocalDataSource
import com.jorgesm.themoviedb.ui.FakeLocationDataSource
import com.jorgesm.themoviedb.ui.FakePermissionChecker
import com.jorgesm.themoviedb.ui.FakeRemoteDataSource

fun buildRepositoryWith(
    localData: List<DomainMovie>,
    remoteData: List<DomainMovie>
): MoviesRepository{
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    
    val localDataSource = FakeLocalDataSource().apply { inMemoryMovies.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
    return  MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    
}