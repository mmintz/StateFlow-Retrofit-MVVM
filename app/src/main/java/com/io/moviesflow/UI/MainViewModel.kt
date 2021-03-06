package com.io.moviesflow.UI

import androidx.lifecycle.*
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MainViewModel() : ViewModel() {

    val movies: LiveData<List<Movie>> get() = MainRepository.movies

    fun changeLike(movie:Movie) = MainRepository.changeMovieLike(movie = movie)

    fun switchClicked(year: Int?) {

        MainRepository.applyFiltering(year)
    }

    val sortedMovies : LiveData<List<Movie>> get() = MainRepository.moviesByYear

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }

    }

}

