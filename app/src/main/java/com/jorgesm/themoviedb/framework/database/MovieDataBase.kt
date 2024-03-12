package com.jorgesm.themoviedb.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}