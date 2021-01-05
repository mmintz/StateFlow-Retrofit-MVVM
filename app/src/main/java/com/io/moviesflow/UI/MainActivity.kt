package com.io.moviesflow.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.R
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MainActivity : AppCompatActivity()  {


    private lateinit var adapter : MoviesAdapter
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        subscribeUI()
    }



    private fun setupUI() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter()
        recyclerView.adapter = adapter
        adapter.onItemClick = { movie ->

            Log.e("Tag", movie.Title + " " + movie.liked)
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
        adapter.likedClicked = { movie ->
            Log.e("Tag", "Liked clicked")
            mainViewModel.changeLike(movie)
        }
    }
    private fun subscribeUI() {
        Log.e("TAG","Main Subbing to view model")
        mainViewModel =  ViewModelProviders.of(this, MainViewModel.Factory()).get(MainViewModel::class.java)
        mainViewModel.movies.observe(this) {
            populateList(it)
            Log.e("Main","Observing")
        }




    }
    private fun populateList(movies: List<Movie>) {
        adapter.addMovies(movies)

    }

    private fun addMovieChange()
    {
        adapter.notifyDataSetChanged()
    }
}