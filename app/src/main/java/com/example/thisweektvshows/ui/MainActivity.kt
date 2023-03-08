package com.example.thisweektvshows.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thisweektvshows.R
import com.example.thisweektvshows.adapters.MovieAdapter
import com.example.thisweektvshows.models.Movie
import com.example.thisweektvshows.util.Resource
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MoviesViewModel>()
    lateinit var moviesAdapter: MovieAdapter
    lateinit var rv: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var favouritedMovie: FloatingActionButton
    var moviesList = arrayListOf<Movie>()
    private lateinit var differ: AsyncListDiffer<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)
        //try to use view binding

        setupRecyclerView()

        viewModel.trendingMovies.observe(this, Observer { response ->
            when (response) {
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
                      //  Log.e("MainActivity", "there is error: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText)
                return true
            }

        })

    }

    private fun setupRecyclerView() {
        rv = findViewById(R.id.movies_rv)
        moviesAdapter = MovieAdapter(moviesList)
      /*  moviesAdapter.onCLick = { item ->
            item.getId


        }*/
        differ = AsyncListDiffer(moviesAdapter, MyDiffCallback())
        val linearLayoutManager = GridLayoutManager(this, 2)
        rv.apply {
            adapter = moviesAdapter
            layoutManager = linearLayoutManager
        }

    }


    private fun hideProgressBar() {
        progressBar = findViewById(R.id.paginationProgressBar)
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar = findViewById(R.id.paginationProgressBar)
        progressBar.visibility = View.VISIBLE
    }
}