package com.io.moviesflow.API

import androidx.lifecycle.MutableLiveData
import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MoviesService {

    companion object {
        val BASE_URL = "https://www.omdbapi.com/"

        private val logging = HttpLoggingInterceptor()

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient().newBuilder().addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC)).build())
                    .build()
        }
        val apiService: Api = getRetrofit().create(Api::class.java)
    }

    suspend fun getMovies(timestamp: String, apiKey: String, movieType: String) = apiService.getMovies(timestamp,apiKey,movieType)
}

interface Api {

    @GET(".")
    suspend fun getMovies(@Query("s")  timestamp: String, @Query("apikey") apikey: String, @Query("type") movieType: String) : SearchResult

}