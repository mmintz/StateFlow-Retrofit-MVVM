package com.io.moviesflow.UI

import android.media.Image
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.io.moviesflow.R
import com.io.moviesflow.data.Movie
import java.util.Collections.addAll

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var moviesList: List<Movie> = emptyList()
    var onItemClick: ((Movie) -> Unit)? = null
    var likedClicked: ((Movie)-> Unit)? = null

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        private val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
        private val movieLiked: ImageView = itemView.findViewById(R.id.movie_like)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(moviesList[adapterPosition])
            }
            movieLiked.setOnClickListener {

                likedClicked?.invoke(moviesList[adapterPosition])
            }
        }
        fun bind(movie : Movie)
        {
            itemView.apply {
                movieTitle.text = movie.Title
                if(movie.liked) {
                    movieLiked.setImageResource(R.drawable.ic_adnroid_like_24dp_red)
                }
                else{
                    movieLiked.setImageResource(R.drawable.ic_android_like_24dp)
                }
                Glide.with(movieImage.context)
                        .load(movie.Poster)
                        .into(movieImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun addMovies(movies: List<Movie>)
    {
        moviesList = movies
        notifyDataSetChanged()
    }


}