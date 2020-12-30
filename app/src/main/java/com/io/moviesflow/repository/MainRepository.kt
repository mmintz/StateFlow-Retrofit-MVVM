package com.io.moviesflow.repository

import androidx.lifecycle.LiveData
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.data.Movie

class MainRepository(private val apiService: MoviesService) {

    suspend fun getMovies() = apiService.getMovies("tt3896198", "2a4d1b6b")

}