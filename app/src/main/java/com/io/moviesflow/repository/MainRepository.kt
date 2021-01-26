package com.io.moviesflow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.ResponseBody


class MainRepository {

    companion object Instance {

/*
        private var  _moviesList : LiveData<List<Movie>> = liveData {
            emit(getMovies().moviesList)
        }
*/
        private val _moviesList = MutableLiveData<List<Movie>>(emptyList())
        private val _moviesByYear = MutableLiveData<List<Movie>>(emptyList())



        init {
            GlobalScope.launch {
               /// delay(2000)
                val listofMovies = getMovies().value?.moviesList
                Log.e("init of repo ", (listofMovies?.get(0)?.Year.toString()))
               _moviesList.postValue( listofMovies)
                Log.e("init of repo ", "postedValue")


            }
        }
//        fun getMovies2() : Flowable<SearchResult>
//        {
//            apiService.getMovies("game","2a4d1b6b","movie")
//        }

        @JvmName("getMovies1")
        fun getMovies() : LiveData<SearchResult> {
             return LiveDataReactiveStreams.fromPublisher(MoviesService.requestApi
                .getMovies("game", "2a4d1b6b", "movie")
                .subscribeOn(Schedulers.io()))
        }

//        fun makeReactiveQuery(): LiveData<ResponseBody?>? {
//            return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getRequestApi()
//                .makeQuery()
//                .subscribeOn(Schedulers.io()))
//        }

                //= ApiService.getMoviesObservable("game","2a4d1b6b","movie") //apiService.getMovies("game", "2a4d1b6b","movie")


        val movies: LiveData<List<Movie>> = _moviesList  //this is a reference

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

        //TODO: what happens if i call getMovies? Do we update the movieslist we have here ? no

        val moviesByYear: LiveData<List<Movie>> = _moviesByYear
        //TODO: To map tha treksei sto main thread? to Taransform


        fun applyFiltering(year: Int?)
        {
            //TODO: giati epimenei oti prepei na en unit
            if(year != null) {
                val tempMovies = movies.value?.filter { movie ->
                    movie.Year.toInt() < year
                }
                _moviesByYear.postValue(tempMovies)
            }
            else {
                _moviesList.postValue(movies.value)
            }
               //setting the value but from any thread ...setValue does the same but only from UI thread
        }


            //_moviesByYear.value = moviesByYearTemp

           // _moviesByYear.postValue(tempMovies)



    }

}