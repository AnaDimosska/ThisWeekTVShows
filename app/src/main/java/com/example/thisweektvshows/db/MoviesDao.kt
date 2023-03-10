package com.example.thisweektvshows.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thisweektvshows.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert
    suspend fun insertFavouriteMovies(movie:Movie): Long

    @Query("SELECT * FROM movie")
    fun getAllFavouritedMovies(): Flow<List<Movie>>
}