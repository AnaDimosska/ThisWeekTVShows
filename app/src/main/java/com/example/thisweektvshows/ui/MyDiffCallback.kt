package com.example.thisweektvshows.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.thisweektvshows.models.Movie

class MyDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}