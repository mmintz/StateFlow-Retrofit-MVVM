package com.io.moviesflow.UI

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.R
import com.io.moviesflow.data.Movie
import com.io.moviesflow.repository.MainRepository

class MovieActivity : AppCompatActivity() {

    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieDetails: TextView
    private lateinit var movieLiked : ImageView
    private lateinit var movie:Movie

    private lateinit var  movieViewModel : MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
        val intent : Intent = getIntent()
        setupUi(intent)
        subscribeUI()

    }



    private fun subscribeUI() {
        Log.e("TAG","Main Subbing to view model")
        movieViewModel =  ViewModelProviders.of(this, MovieViewModel.Factory()).get(MovieViewModel::class.java)


    }
    private fun setupUi(intent: Intent) {

        movie= intent.getParcelableExtra("movie")!!

        movieImage = findViewById(R.id.movie_image)
        movieTitle = findViewById(R.id.movie_title)
        movieTitle.text = movie?.Title

        movieDetails = findViewById(R.id.movie_details)

        movieDetails.text = movie?.Year+" " +movie?.Type
        Glide.with(movieImage.context)
                .load(movie?.Poster)
                .into(movieImage)

        movieLiked = findViewById(R.id.movie_like)

        if(movie?.liked == true) {
            movieLiked.setImageResource(R.drawable.ic_adnroid_like_24dp_red)

        }
        else{
            movieLiked.setImageResource(R.drawable.ic_android_like_24dp)
        }

        movieLiked.setOnClickListener{
            if(movie?.liked == true) {
                movieLiked.setImageResource(R.drawable.ic_android_like_24dp)
            }
            else{
                movieLiked.setImageResource(R.drawable.ic_adnroid_like_24dp_red)
            }

            movieViewModel.changeLike(movie)
        }
    }
}