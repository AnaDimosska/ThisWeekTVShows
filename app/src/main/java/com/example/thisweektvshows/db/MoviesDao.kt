package com.example.thisweektvshows.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.thisweektvshows.models.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie")
    fun getAllTrendingMovies(): LiveData<List<Movie>>
}