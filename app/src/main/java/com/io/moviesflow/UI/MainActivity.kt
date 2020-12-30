package com.io.moviesflow.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.R
import com.io.moviesflow.repository.MainRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeUI()
    }

    private fun subscribeUI() {
       val mainViewModel =  ViewModelProviders.of(this, MainViewModel.Factory(MainRepository(MoviesService()))).get(MainViewModel::class.java)
        mainViewModel.movies.observe(this) {
                println(it)
        }
    }
}