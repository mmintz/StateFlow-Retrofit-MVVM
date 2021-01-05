package com.io.moviesflow.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SearchResult(
        val Response: String,
        @SerializedName("Search")
        val moviesList: List<Movie>,
        val totalResults: String
)
@Parcelize
data class Movie(
        val Poster: String,
        val Title: String,
        val Type: String,
        val Year: String,
        val imdbID: String,
        val liked : Boolean
) : Parcelable

