package com.example.thisweektvshows.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thisweektvshows.R
import com.example.thisweektvshows.adapters.MovieAdapter
import com.example.thisweektvshows.db.MoviesDatabase
import com.example.thisweektvshows.models.Movie
import com.example.thisweektvshows.repo.MoviesRepository
import com.example.thisweektvshows.util.Resource
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MoviesViewModel
    lateinit var moviesAdapter:MovieAdapter
    lateinit var rv:RecyclerView
    lateinit var progressBar:ProgressBar
    lateinit var searchView:androidx.appcompat.widget.SearchView
    lateinit var favouritedMovie: FloatingActionButton
    var moviesList = arrayListOf<Movie>()
    private lateinit var differ: AsyncListDiffer<Movie>

    @Inject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        val repo = MoviesRepository(MoviesDatabase(this))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(repo)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MoviesViewModel::class.java)

        setupRecyclerView()

        viewModel.trendingMovies.observe(this, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { moviesResponse ->
                        moviesAdapter.differ.submitList(moviesResponse)
                        moviesList.addAll(moviesResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        Log.e("MainActivity","there is error: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
              moviesAdapter.filter.filter(newText)
                return true
            }

        })

    }

    private fun setupRecyclerView(){
        rv = findViewById(R.id.movies_rv)
        moviesAdapter = MovieAdapter(moviesList)
        differ = AsyncListDiffer(moviesAdapter, MyDiffCallback())
        val linearLayoutManager = GridLayoutManager(this,2)
        rv.apply {
            adapter = moviesAdapter
            layoutManager = linearLayoutManager
        }

    }


    private fun hideProgressBar(){
        progressBar = findViewById(R.id.paginationProgressBar)
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar = findViewById(R.id.paginationProgressBar)
        progressBar.visibility = View.VISIBLE
    }
}