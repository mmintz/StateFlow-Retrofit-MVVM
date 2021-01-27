package com.io.moviesflow.repository

import android.util.Log
import androidx.lifecycle.*
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
        private val _moviesList : LiveData<List<Movie>>
        private val _moviesByYear = MutableLiveData<List<Movie>>(emptyList())
        private val yearFilter = MutableLiveData<Int?>()
        private val combinedMovies = MediatorLiveData<List<Movie>>()

        private val likedMovies = MutableLiveData<Set<String>>(emptySet())

        init {
            //otan kalestei thelw na m to kameis transfrom. En na kalestei otan to kamei observe kapios
            _moviesList = Transformations.map(getMovies()){
                it.moviesList
            }
            //kaliounte sto UI thread gft en evalame post value
            combinedMovies.addSource(_moviesList){ combinedMovies.value = combine()}
            combinedMovies.addSource(yearFilter){ combinedMovies.value = combine() }
            combinedMovies.addSource(likedMovies){ combinedMovies.value = combine() }
        }

        private fun combine() : List<Movie>{
            //an den eshis movies dwke null alliws ta movies
            val movies = _moviesList.value ?: return emptyList()
            val year = yearFilter.value
            val m = if(year != null)  movies.filter { it.Year.toInt() < year } else movies
            return m.map {
                if(likedMovies.value!!.contains(it.imdbID)) it.copy(liked = true) else it

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

            val likedMovie = likedMovies.value!!
            if(likedMovie.contains(movie.imdbID))
                likedMovies.value = likedMovie.minus(movie.imdbID) //epistrefei neo set xwris touto to element
            else
                likedMovies.value = likedMovie.plus(movie.imdbID)

//            val newMovies = movies.value?.map {
//                if(it.Title == movie.Title)
//                {
//                    it.copy(liked = !it.liked)
//                }
//                else{
//                    it
//                }
//            }
//
//           moviesByYear.postValue(newMovies)    //TODO IS THIS IN THE UI ???
            //movies.value?.find { it.Title == movie.Title }?.let { it.liked = !(it.liked) }
        }

        //TODO: what happens if i call getMovies? Do we update the movieslist we have here ? no

        val moviesByYear: LiveData<List<Movie>> = combinedMovies
        //TODO: To map tha treksei sto main thread? to Taransform


        fun applyFiltering(year: Int?)
        {
            yearFilter.postValue(year)
               //setting the value but from any thread ...setValue does the same but only from UI thread
        }


            //_moviesByYear.value = moviesByYearTemp

           // _moviesByYear.postValue(tempMovies)



    }

}