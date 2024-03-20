package com.jorgesm.themoviedb.di

import android.app.Application
import com.jorgesm.themoviedb.ui.detail.DetailFragment
import com.jorgesm.themoviedb.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, AppDataModule::class]
)
interface AppComponent {
    
    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailFragment)
    
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AppComponent
    }
}