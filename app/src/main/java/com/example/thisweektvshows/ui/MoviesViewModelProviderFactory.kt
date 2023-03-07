package com.example.thisweektvshows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thisweektvshows.repo.MoviesRepository

class MoviesViewModelProviderFactory(
    val moviesRepo:MoviesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepo) as T
    }
}