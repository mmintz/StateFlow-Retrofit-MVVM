package com.io.moviesflow.UI

import androidx.lifecycle.*
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _movies = liveData<List<Movie>> {
        emit(repository.getMovies().moviesList)
    }

    val movies: LiveData<List<Movie>> get() = _movies

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: MainRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }

    }

}

