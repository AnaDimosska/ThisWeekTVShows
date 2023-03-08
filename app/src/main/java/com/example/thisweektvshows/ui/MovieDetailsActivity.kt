package com.example.thisweektvshows.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import com.bumptech.glide.Glide
import com.example.thisweektvshows.R
import com.example.thisweektvshows.util.FavoriteClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class MovieDetailsActivity : AppCompatActivity() {
    private var flag:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieImage = findViewById<ImageView>(R.id.movie_poster)
        val movieName = findViewById<TextView>(R.id.movie_title)
        val movieDescription = findViewById<TextView>(R.id.movie_overview)
        val intent = intent

        Glide.with(this).load(intent.getStringExtra("movie_image")).into(movieImage)
        movieName.text = intent.getStringExtra("movie_name")
        movieDescription.text = intent.getStringExtra("movie_desc")


    }
}