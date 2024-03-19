package com.jorgesm.themoviedb

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.PlayServicesLocationDataSource
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.data.database.MovieDataBase
import com.jorgesm.themoviedb.data.database.MovieRoomDataSource
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import com.jorgesm.themoviedb.data.datasource.MovieLocalDataSource
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.data.server.AndroidPermissionChecker
import com.jorgesm.themoviedb.data.server.MovieServerDataSource
import com.jorgesm.themoviedb.ui.detail.DetailViewModel
import com.jorgesm.themoviedb.ui.main.MainViewModel
import com.jorgesm.themoviedb.usecases.GetMovieByIdUseCase
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.SetMovieFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun Application.initKoinDI(){
    
    startKoin{
        androidLogger(Level.ERROR)
        androidContext(this@initKoinDI)
        modules(appModule, dataModule, useCasesModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { androidContext().getString(R.string.api_key) }
    
    single { Room.databaseBuilder(
            get(),
            MovieDataBase::class.java, "movie-db"
        ).build()
    }
    
    single { get<MovieDataBase>().movieDao() }
    
    factoryOf(::MovieRoomDataSource) bind MovieLocalDataSource::class
    factory<MovieRemoteDataSource> { MovieServerDataSource(get(named("apiKey")))}
    
    factoryOf(::PlayServicesLocationDataSource) bind LocationDataSource::class
    factoryOf(::AndroidPermissionChecker) bind PermissionChecker::class
    
    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)
}

private val dataModule = module {
    factoryOf(::RegionRepository)
    factoryOf(::MoviesRepository)
}

private val useCasesModule = module {
    factoryOf(::GetMovieByIdUseCase)
    factoryOf(::GetPopularMoviesUseCase)
    factoryOf(::RequestPopularMoviesUseCase)
    factoryOf(::SetMovieFavoriteUseCase)
}