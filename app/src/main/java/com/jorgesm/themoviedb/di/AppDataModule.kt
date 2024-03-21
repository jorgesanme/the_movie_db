package com.jorgesm.themoviedb.di

import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.PlayServicesLocationDataSource
import com.jorgesm.themoviedb.data.database.MovieRoomDataSource
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.data.server.AndroidPermissionChecker
import com.jorgesm.themoviedb.data.server.MovieServerDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule{
    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSource: MovieServerDataSource): MovieRemoteDataSource
    @Binds
    abstract fun bindsLocalDataSource(localDataSource: MovieRoomDataSource): MovieLocalDataSource
    @Binds
    abstract fun bindsPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
    @Binds
    abstract fun bindsLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource
    
}