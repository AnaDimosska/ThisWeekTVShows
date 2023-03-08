package com.example.thisweektvshows.repo

import com.example.thisweektvshows.api.RetrofitInstance
import com.example.thisweektvshows.db.MoviesDatabase

class MoviesRepository(
    db:MoviesDatabase
) {

    suspend fun getTrendingMovies(api:String) =
        RetrofitInstance.api.getPopularMovies(api)

}