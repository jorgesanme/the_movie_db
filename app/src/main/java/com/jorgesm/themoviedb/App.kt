package com.jorgesm.themoviedb

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinDI()
    }
}