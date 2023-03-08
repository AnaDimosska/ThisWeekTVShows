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
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    val moviesRepo: MoviesRepository
): ViewModel() {

    val trendingMovies:MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    init {
        getTrendingMovies(API_KEY)
    }

    fun getTrendingMovies(api:String) = viewModelScope.launch {
        trendingMovies.postValue(Resource.Loading())
        val response = moviesRepo.getTrendingMovies(api)
      //  response.body().let {
          //  response.body()?.results?.forEach { movie ->
          //        moviesRepo.movieDao.insertMovies(movie)
         //   }
      //  }
       // response.body()?.let { moviesRepo.movieDao.insertMovies(it.results.) }
        trendingMovies.postValue(handleResponse(response))
    }


    /**
     * This handle response we can choose if we get straight from api or from room Resource.Success(movieResponse?.results)
     */
    private fun handleResponse(response: Response<MovieResponse>): Resource<List<Movie>>{
        if(response.isSuccessful){
            response.body().let { movieResponse->
               // return Resource.Success(moviesRepo.movieDao.getAllTrendingMovies())
                return Resource.Success(movieResponse?.results)
            }
        }
        return Resource.Error(response.message())
    }


}