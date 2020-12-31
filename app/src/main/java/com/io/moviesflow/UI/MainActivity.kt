package com.io.moviesflow.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.R
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MainActivity : AppCompatActivity() {


    private lateinit var adapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        subscribeUI()
    }

    private fun setupUI() {
        val recyclerView: RecyclerView = findViewById(R.id.moviesView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter()
        recyclerView.adapter = adapter
    }

    private fun subscribeUI() {
       val mainViewModel =  ViewModelProviders.of(this, MainViewModel.Factory(MainRepository(MoviesService()))).get(MainViewModel::class.java)
        mainViewModel.movies.observe(this) {
            populateList(it)
        }
    }
    private fun populateList(movies: List<Movie>) {
        adapter.addMovies(movies)

    }
}