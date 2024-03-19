package com.jorgesm.themoviedb.di

import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {
    @Provides
    fun provideRegionRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)
    
    @Provides
    fun provideMoviesRepository(
        regionRepository: RegionRepository,
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource
    ) = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    
}

