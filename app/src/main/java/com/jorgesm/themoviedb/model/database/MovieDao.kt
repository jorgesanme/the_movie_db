package com.jorgesm.themoviedb.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getAllMovies(): Flow<List<Movie>>
    
    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findMovieById(id: Int): Flow<Movie>
    
    @Query("SELECT COUNT(id) FROM Movie")
    fun countMovies(): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<Movie>)
    
    @Update
    fun updateMovie(movie: Movie)
}