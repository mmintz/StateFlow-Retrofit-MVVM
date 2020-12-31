package com.io.moviesflow.data

import com.google.gson.annotations.SerializedName

data class SearchResult(
        val Response: String,
        @SerializedName("Search")
        val moviesList: List<Movie>,
        val totalResults: String
)

data class Movie(
        val Poster: String,
        val Title: String,
        val Type: String,
        val Year: String,
        val imdbID: String
)

