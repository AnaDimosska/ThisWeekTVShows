package com.example.thisweektvshows.util

import android.view.View

interface FavoriteClickListener {
    fun onButtonClick(view: View, data: String)
}