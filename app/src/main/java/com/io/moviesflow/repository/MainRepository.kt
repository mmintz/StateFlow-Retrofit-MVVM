package com.io.moviesflow.repository

import android.util.Log
import androidx.lifecycle.*
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import io.reactivex.functions.*
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlin.Throwable as Throwable1


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
            _moviesList = getMovies()
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

        /**
         * Probably instead of using from publisher here we return a floable.
         * Inside here we subscribe to it to this result ennow on Next we will getr something
         * onError we will handle it there
         * On complete we will do whatever we do. Where is live data ?
         */


        @JvmName("getMovies1")
        fun getMovies() : LiveData<List<Movie>> {
             return LiveDataReactiveStreams.fromPublisher(
                 MoviesService.requestApi.getMovies("game", "2a4d1b6b", "movie")
                     .onErrorReturn(io.reactivex.functions.Function<kotlin.Throwable, SearchResult?>{
                         Log.e("RepoObservable","error")
                         SearchResult("error", listOf(Movie("asa","Test","movie","2000","id",true)),"0")
                         /**En ola chained
                            evala ena movie mes to list sto map p ta epistrefei edike mono tsino
                           prepei eite na kamoume throw exception and handle it or something else
                          check it out
                          */

                         })
                     .map(io.reactivex.functions.Function<SearchResult, List<Movie>> {
                         it.moviesList

                     })
                     .subscribeOn(Schedulers.io()))
        }


//        fun makeAPICall(): LiveData<String?>? {
//            return LiveDataReactiveStreams.fromPublisher(
//                sampleAPI.getFlowableResponse()
//                    .onErrorReturn(object : Function<Throwable1?, BoredActivities?>() {
//                        @Throws(Exception::class)
//                        fun apply(throwable: Throwable1?): BoredActivities? {
//                            val boredActivities = BoredActivities()
//                            boredActivities.setLink("")
//                            return boredActivities
//                        }
//                    })
//                    .map(object : Function<BoredActivities?, String?>() {
//                        @Throws(Exception::class)
//                        fun apply(boredActivities: BoredActivities): String? {
//                            return boredActivities.getLink()
//                        }
//                    })
//                    .subscribeOn(Schedulers.io()))
//        }


        fun changeMovieLike(movie: Movie) {

            val likedMovie = likedMovies.value!!
            if(likedMovie.contains(movie.imdbID))
                likedMovies.value = likedMovie.minus(movie.imdbID) //epistrefei neo set xwris touto to element
            else
                likedMovies.value = likedMovie.plus(movie.imdbID)


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