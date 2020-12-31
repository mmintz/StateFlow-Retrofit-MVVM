package com.io.moviesflow.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.io.moviesflow.R

class MovieActivity : AppCompatActivity() {

    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieDetails: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
        val intent : Intent = getIntent()
        setupUi(intent)

    }

    private fun setupUi(intent: Intent) {


        val imageUrl: String? = intent.getStringExtra("imageId")
        val title: String? = intent.getStringExtra("title")
        val year: String? = intent.getStringExtra("year")
        val type: String? = intent.getStringExtra("type")


        movieImage = findViewById(R.id.movie_image)
        movieTitle = findViewById(R.id.movie_title)
        movieTitle.text = title

        movieDetails = findViewById(R.id.movie_details)

        movieDetails.text = year+" " +type
        Glide.with(movieImage.context)
                .load(imageUrl)
                .into(movieImage)
    }
}