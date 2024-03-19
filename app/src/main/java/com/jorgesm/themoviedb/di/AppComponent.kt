package com.jorgesm.themoviedb.di

import android.app.Application
import com.jorgesm.themoviedb.ui.detail.DetailViewModelFactory
import com.jorgesm.themoviedb.ui.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        UseCaseModule::class,
        ViewModelsModule::class
    ]
)
interface AppComponent {
    
    val mainViewModelFactory: MainViewModelFactory
    val detailViewModelFactory: DetailViewModelFactory
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AppComponent
    }
}