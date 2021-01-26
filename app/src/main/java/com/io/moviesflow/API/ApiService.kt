package com.io.moviesflow.API

import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

object ApiService {


    var observer: DisposableObserver<*> = object : DisposableObserver<SearchResult?>() {
        override fun onNext(s: SearchResult) {

        }
        override fun onComplete() {}
        override fun onError(t: Throwable) {


        }
    }

    //fun getMoviesObservable(timestamp: String, apiKey: String, movieType: String) : Observable<SearchResult> {
     //   return MoviesService.apiService.getMovies(timestamp,apiKey,movieType)


    //}



    //private lateinit var disposableObserver: DisposableObserver<SearchResult>


    //fun getMovies(timestamp: String, apiKey: String, movieType: String){
       // disposableObserver.



        ///MoviesService.apiService.getMovies(timestamp,apiKey,movieType)
          //  .





}