package com.io.moviesflow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.liveData
import com.io.moviesflow.API.Api
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.API.MoviesService.Companion.apiService
import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {

    companion object Instance {

/*
        private var  _moviesList : LiveData<List<Movie>> = liveData {
            emit(getMovies().moviesList)
        }
*/
        private val _moviesList = MutableLiveData<List<Movie>>(emptyList())

        init {
            GlobalScope.launch {
                _moviesList.postValue(getMovies().moviesList)
            }
        }

        /**
         * egw gamw to kolo sou re manno companion object
         */
        //private var  _moviesList :LiveData<List<Movie>> = liveData {
        //    emit(getMovies().moviesList)
        //    Log.e("TagMainRepo","Calling getMovies()")
       // }
        suspend fun getMovies() = apiService.getMovies("game", "2a4d1b6b")


        val movies: LiveData<List<Movie>> = _moviesList  //this is a reference

        fun changeMovieLike(movie: Movie) {
            val newMovies = movies.value?.map {
                if(it.Title == movie.Title)
                {
                    it.copy(liked = ! it.liked)
                }
                else{
                    it
                }
            }
            _moviesList.postValue(newMovies)
            //movies.value?.find { it.Title == movie.Title }?.let { it.liked = !(it.liked) }
        }

    }






}