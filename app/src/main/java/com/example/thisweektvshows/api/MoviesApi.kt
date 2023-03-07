package com.example.thisweektvshows.api

import com.example.thisweektvshows.models.MovieResponse
import com.example.thisweektvshows.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    // url
    //https://api.themoviedb.org/3/trending/all/day?api_key=c27c208f49b0acfb67198de27f36d571

    @GET("/3/trending/all/day")
    suspend fun getPopularMovies(@Query("api_key") accessToken: String = API_KEY): Response<MovieResponse>


}