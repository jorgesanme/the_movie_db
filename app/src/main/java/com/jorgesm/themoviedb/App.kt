package com.jorgesm.themoviedb

import android.app.Application
import androidx.room.Room
import com.jorgesm.themoviedb.framework.database.MovieDataBase

class App: Application() {
    lateinit var db: MovieDataBase
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        db = Room.databaseBuilder(
             this,
             MovieDataBase::class.java,"movie-db"
        ).build()
    }
}