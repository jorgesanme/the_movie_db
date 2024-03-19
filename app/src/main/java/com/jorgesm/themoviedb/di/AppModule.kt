package com.jorgesm.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.data.database.MovieDataBase
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
    @Singleton
    fun provideMovieDao(db: MovieDataBase) = db.movieDao()
}
