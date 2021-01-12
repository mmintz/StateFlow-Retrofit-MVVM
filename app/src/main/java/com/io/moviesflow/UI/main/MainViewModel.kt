package com.io.moviesflow.UI.main

import androidx.lifecycle.*
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MainViewModel() : ViewModel() {

    val movies: MediatorLiveData<List<Movie>> get() = MainRepository.INSTANCE.mediatorMovies

    fun changeLike(movie:Movie) = MainRepository.INSTANCE.changeMovieLike(movie = movie)

    fun switchClicked(year: Int?) {

        MainRepository.INSTANCE.applyFiltering(year)

    }

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }

    }

}

