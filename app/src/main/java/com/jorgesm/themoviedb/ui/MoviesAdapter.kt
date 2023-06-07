package com.jorgesm.themoviedb.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorgesm.themoviedb.Movie
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.ItemMovieBinding
import com.jorgesm.themoviedb.utils.Constants
import com.jorgesm.themoviedb.utils.inflate
import com.jorgesm.themoviedb.utils.loadUrl

class MoviesAdapter(
    private val moviesList: List<Movie>,
    private val listener: (Movie)-> Unit
):RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_movie, false)
        return ViewHolder(view)
    }
    
    override fun getItemCount(): Int = moviesList.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( moviesList[position])
    }
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        private val binding = ItemMovieBinding.bind(view)
        fun bind(movie: Movie){
            with(binding){
                movieCover.loadUrl(Constants.IMG_BASE_URL+Constants.IMG_185+movie.posterPath)
                movieTitle.text = movie.title
                itemView.setOnClickListener{listener(movie)}
            }
        }
        
    }
    
}