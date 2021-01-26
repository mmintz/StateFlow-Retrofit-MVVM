package com.io.moviesflow.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.io.moviesflow.R
import com.io.moviesflow.data.Movie
import com.io.moviesflow.data.SearchResult
import okhttp3.ResponseBody
import java.io.IOException

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

        val switch: SwitchCompat = findViewById(R.id.switch_new)

        switch.setOnClickListener{
            if(switch.isChecked) {
                mainViewModel.switchClicked(2015)
            }
            else{
                mainViewModel.switchClicked(null)
            }
        }

    }
    private fun subscribeUI() {
        Log.e("TAG", "Main Subbing to view model")
        mainViewModel =
            ViewModelProviders.of(this, MainViewModel.Factory()).get(MainViewModel::class.java)
            Thread.sleep(3000)
//        viewModel.makeQuery().observe(this,
//            Observer<ResponseBody> { responseBody ->
//                Log.d(TAG, "onChanged: this is a live data response!")
//                try {
//                    Log.d(TAG, "onChanged: " + responseBody.string())
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            })
        Log.e("MainActivity","starting to observe")
        mainViewModel.movies.observe(this,
            androidx.lifecycle.Observer<SearchResult>() { searchRes ->

                Log.e("Main", "puplating")
                try {

                    searchRes.moviesList.forEach { movie ->
                        Log.e("Main", "Year " + movie.Year)
                        populateList(searchRes.moviesList)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })

        mainViewModel.sortedMovies.observe(this) {
            populateList(it)
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