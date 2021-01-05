package com.io.moviesflow.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MovieViewModel() : ViewModel() {

    /*
    private val _movies = liveData<List<Movie>> {
        emit(repository.getMovies().moviesList)
    }

    val movies: LiveData<List<Movie>> get() = _movies
*/
    fun changeLike(movie:Movie) = MainRepository.changeMovieLike(movie = movie)

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel() as T
        }

    }

}