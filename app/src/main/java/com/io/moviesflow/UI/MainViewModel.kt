package com.io.moviesflow.UI

import androidx.lifecycle.*
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository
import com.io.moviesflow.repository.MainRepository.Instance.getMovies

class MainViewModel() : ViewModel() {



    //private val movieListener = Observer<List<Movie>> {  }
/*
    init {
        MainRepository.movies.observeForever(movieListener)
    }
  */
    /*
    override fun onCleared() {
        MainRepository.movies.removeObserver(movieListener)
        super.onCleared()
    }
    */
    val movies: LiveData<List<Movie>> get() = MainRepository.movies

    fun changeLike(movie:Movie) = MainRepository.changeMovieLike(movie = movie)

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }

    }

}

