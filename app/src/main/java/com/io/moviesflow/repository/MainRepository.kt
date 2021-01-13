package com.io.moviesflow.repository

import androidx.lifecycle.*
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.data.Movie
import kotlinx.coroutines.*
import java.lang.Exception


class MainRepository(private val apiService: MoviesService,private val scope: CoroutineScope = GlobalScope) {

    private val _moviesList = MutableLiveData<List<Movie>>(emptyList())
    //private val _moviesByYear = MutableLiveData<List<Movie>>(emptyList())
    private val _liveDataMerger = MediatorLiveData<List<Movie>>()
    private val _year = MutableLiveData<Int>()

    val movies: LiveData<List<Movie>> = _moviesList  //this is a reference
    val mediatorMovies: MediatorLiveData<List<Movie>> = _liveDataMerger


    //TODO: what happens if i call getMovies? Do we update the movieslist we have here ? no

    //val moviesByYear: LiveData<List<Movie>> = _moviesByYear
    //TODO: To map tha treksei sto main thread? to Taransform


    fun applyFiltering(year: Int?) {
        _year.value = year
    }


    init {

        val combine = {
            val movies = _moviesList.value
            val year = _year.value
            if (year == null) {
                _liveDataMerger.value = movies
            } else {
                _liveDataMerger.value = movies?.filter { movie ->
                    movie.Year.toInt() < year
                }
            }
        }

        _liveDataMerger.addSource(_moviesList) {
            combine()
        }

        _liveDataMerger.addSource(_year) {
            combine()
        }

        scope.launch {
           // delay(5000)

            val result = try {
                getMovies().moviesList //Todo afou en suspsend
            } catch (e: Exception) {
                println("Exception " + e.message)
                emptyList<Movie>()
            }
            _moviesList.postValue(result)
        }
    }

    private suspend fun getMovies() = apiService.getMovies("game", "2a4d1b6b", "movie")


    fun changeMovieLike(movie: Movie) {
        val newMovies = movies.value?.map {
            if(it.Title == movie.Title)
            {
                it.copy(liked = !it.liked)
            }
            else{
                it
            }
        }

        _moviesList.postValue(newMovies)    //TODO IS THIS IN THE UI ???
        //movies.value?.find { it.Title == movie.Title }?.let { it.liked = !(it.liked) }
    }
    companion object  {
        val INSTANCE = MainRepository(MoviesService())

    }

}