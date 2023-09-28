package com.jorgesm.themoviedb.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jorgesm.themoviedb.model.Movie
import com.jorgesm.themoviedb.databinding.ActivityDetailBinding
import com.jorgesm.themoviedb.utils.Constants
import com.jorgesm.themoviedb.utils.loadUrl

class DetailActivity: AppCompatActivity() {
    
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
       intent.getSerializableExtra(Constants.MOVIE)?.run{
            this as Movie
            val movie = this
            val background = backdropPath ?: posterPath
            with (binding){
                movieDetailToolbar.title = title
                detailImage.loadUrl(Constants.IMG_BASE_URL+Constants.IMG_780+background)
                movieDetailSummary.text = overview
                movieDetailInfo.setMovieDetailText(movie)
            }
       }
    }
}