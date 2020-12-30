package com.io.moviesflow.API

import com.io.moviesflow.data.Movie
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

    suspend fun getMovies(timestamp: String, apiKey: String) = apiService.getMovies(timestamp,apiKey)
}

interface Api {
    //?i=tt3896198&apikey=2a4d1b6b

    @GET(".")
    suspend fun getMovies(@Query("i")  timestamp: String, @Query("apikey") apikey: String) : Movie

}