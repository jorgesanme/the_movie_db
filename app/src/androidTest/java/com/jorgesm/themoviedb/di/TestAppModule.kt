package com.jorgesm.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.data.database.MovieDao
import com.jorgesm.themoviedb.data.database.MovieDataBase
import com.jorgesm.themoviedb.data.server.RemoteService
import com.jorgesm.themoviedb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    @ApiUrl
    fun provideApiUrl(): String = Constants.TEST_BASE_URL
    
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
    fun provideRemoteService(@ApiUrl apiUrl: String): RemoteService{
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }
        
        return  Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}