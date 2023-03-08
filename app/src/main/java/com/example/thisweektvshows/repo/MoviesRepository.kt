package com.example.thisweektvshows.repo

import com.example.thisweektvshows.api.MoviesApi
import com.example.thisweektvshows.db.MoviesDao
import javax.inject.Inject

class MoviesRepository @Inject constructor(
   private val moviesApi: MoviesApi
) {

    suspend fun getTrendingMovies(api:String) =
        moviesApi.getPopularMovies(api)

}