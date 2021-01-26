package com.io.moviesflow.API

import com.io.moviesflow.data.SearchResult
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//class MoviesService {
//
//    companion object {
//        val BASE_URL = "https://www.omdbapi.com/"
//
//        private val logging = HttpLoggingInterceptor()
//
//        private fun getRetrofit(): Retrofit {
//            return Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .client(OkHttpClient().newBuilder().addInterceptor(logging.setLevel(
//                        HttpLoggingInterceptor.Level.BASIC)).build())
//                    .build()
//        }
//        val apiService = getRetrofit().create(Api::class.java)
//    }
//
//    //fun getMovies(timestamp: String, apiKey: String, movieType: String) = apiService.getMovies2(timestamp,apiKey,movieType)
//}

interface Api {

    @GET(".")
    fun getMovies(
        @Query("s") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("type") movieType: String
    ) : Flowable<SearchResult>

    //@GET(".")
    //fun getMovies2(@Query("s")  timestamp: String, @Query("apikey") apikey: String, @Query("type") movieType: String) : SearchResult

}

object MoviesService {
    const val BASE_URL = "https://www.omdbapi.com/"
    private val logging = HttpLoggingInterceptor()
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient().newBuilder().addInterceptor(logging.setLevel(
                            HttpLoggingInterceptor.Level.BASIC)).build())
    private val retrofit = retrofitBuilder.build()
    val requestApi: Api = retrofit.create(Api::class.java)
}