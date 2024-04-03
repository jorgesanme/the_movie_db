package com.jorgesm.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.appTestShared.FakeRemoteService
import com.jorgesm.themoviedb.appTestShared.buildRemoteMovies
import com.jorgesm.themoviedb.data.database.MovieDao
import com.jorgesm.themoviedb.data.database.MovieDataBase
import com.jorgesm.themoviedb.data.server.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestAppModule {
    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(application: Application): String = "1234"
    
    @Provides
    @Singleton
    fun provideDataBase(application: Application) =  Room.inMemoryDatabaseBuilder(
        application,
        MovieDataBase::class.java
    ).build()
    
    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDataBase): MovieDao = db.movieDao()
    
    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService = FakeRemoteService(buildRemoteMovies(1, 2, 3, 4, 5, 6))
}