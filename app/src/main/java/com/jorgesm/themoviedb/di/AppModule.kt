package com.jorgesm.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.PlayServicesLocationDataSource
import com.jorgesm.themoviedb.data.database.MovieDataBase
import com.jorgesm.themoviedb.data.database.MovieRoomDataSource
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.data.server.AndroidPermissionChecker
import com.jorgesm.themoviedb.data.server.MovieServerDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(application: Application): String = application.getString(R.string.api_key)
    
    
    @Provides
    @Singleton
    fun provideDataBase(application: Application) =  Room.databaseBuilder(
        application,
        MovieDataBase::class.java,"movie-db"
    ).build()
    
    @Provides
    fun provideRemoteDataSource(@ApiKey apiKey: String): MovieRemoteDataSource =
        MovieServerDataSource(apiKey)
    
    @Provides
    fun provideLocalDataSource(dataBase: MovieDataBase): MovieLocalDataSource =
        MovieRoomDataSource(dataBase.movieDao())
    @Provides
    fun providePermissionChecker(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
    @Provides
    fun provideLocationDataSource(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)
}

