package com.io.moviesflow.UI

import androidx.lifecycle.*
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MovieViewModel(private val movie: Movie) : ViewModel() {

    /*
    private val _movies = liveData<List<Movie>> {
        emit(repository.getMovies().moviesList)
    }

    val movies: LiveData<List<Movie>> get() = _movies
*/
    val isLiked: LiveData<Boolean> get() = MainRepository.moviesByYear.map { movies ->
        movies.firstOrNull{ it.Title == movie.Title} ?.liked == true
    }


    fun changeLike(movie:Movie) = MainRepository.changeMovieLike(movie = movie)


    @Suppress("UNCHECKED_CAST")
    class Factory(private val movie: Movie) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel(movie) as T
        }

    }

}