package com.jorgesm.themoviedb

import android.app.Application
import com.jorgesm.themoviedb.di.AppComponent
import com.jorgesm.themoviedb.di.DaggerAppComponent

class App : Application() {
    
    lateinit var component: AppComponent
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        component = DaggerAppComponent.factory().create(this)
        
    }
    
}