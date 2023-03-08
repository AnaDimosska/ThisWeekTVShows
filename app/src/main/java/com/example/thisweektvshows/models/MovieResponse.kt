package com.example.thisweektvshows.models

import androidx.annotation.Keep


@Keep //or use moshi codegen
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)