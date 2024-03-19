package com.jorgesm.themoviedb.di

import android.app.Application
import com.jorgesm.themoviedb.ui.detail.DetailFragmentComponent
import com.jorgesm.themoviedb.ui.detail.DetailFragmentModule
import com.jorgesm.themoviedb.ui.main.MainFragmentComponent
import com.jorgesm.themoviedb.ui.main.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
    
    fun plus(mainFragmentModule: MainFragmentModule): MainFragmentComponent
    fun plus(detailFragmentModule: DetailFragmentModule): DetailFragmentComponent
    
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AppComponent
    }
}