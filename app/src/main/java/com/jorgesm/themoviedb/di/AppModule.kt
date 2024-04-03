package com.jorgesm.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.data.database.MovieDataBase
import com.jorgesm.themoviedb.data.server.RemoteService
import com.jorgesm.themoviedb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
    
    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService{
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }
        
        return  Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
        
    }
}
