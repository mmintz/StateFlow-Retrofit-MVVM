package com.io.moviesflow.API

import androidx.lifecycle.MutableLiveData
import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import com.io.moviesflow.repository.MainRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

open class MoviesService {

    private val logging = HttpLoggingInterceptor()
    val BASE_URL = "https://www.omdbapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient().newBuilder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC)).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MoviesServiceApi::class.java)


//        private fun create(): MoviesService {
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//
//                .build()
//
//            return retrofit.create(MoviesServiceApi::class.java)
//        }


     open suspend fun getMovies(timestamp: String, apiKey: String, movieType: String) = apiService.getMovies(timestamp,apiKey,movieType)


}

interface MoviesServiceApi {

    @GET(".")
    suspend fun getMovies(
        @Query("s") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("type") movieType: String,
    ): SearchResult

}