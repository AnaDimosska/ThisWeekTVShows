package com.example.thisweektvshows.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thisweektvshows.models.Movie
import com.example.thisweektvshows.models.MovieResponse
import com.example.thisweektvshows.repo.MoviesRepository
import com.example.thisweektvshows.util.Constants.Companion.API_KEY
import com.example.thisweektvshows.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(
    val moviesRepo: MoviesRepository
): ViewModel() {

    val trendingMovies:MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    init {
        getTrendingMovies(API_KEY)
    }

    fun getTrendingMovies(api:String) = viewModelScope.launch {
        trendingMovies.postValue(Resource.Loading())
        val response = moviesRepo.getTrendingMovies(api)
        trendingMovies.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<MovieResponse>): Resource<List<Movie>>{
        if(response.isSuccessful){
            response.body().let {movieResponse->
                return Resource.Success(movieResponse?.results)
            }
        }
        return Resource.Error(response.message())
    }

}