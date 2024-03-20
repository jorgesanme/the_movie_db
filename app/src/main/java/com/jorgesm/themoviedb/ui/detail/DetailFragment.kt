package com.jorgesm.themoviedb.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentDetailBinding
import com.jorgesm.themoviedb.utils.Constants
import com.jorgesm.themoviedb.utils.loadUrl
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment: Fragment(R.layout.fragment_detail) {
    
    private val safeArgs: DetailFragmentArgs by navArgs()
    
    private val viewModel: DetailViewModel by viewModel {
        parametersOf(safeArgs.movieId)
    }
    
    private lateinit var binding: FragmentDetailBinding
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        binding.movieDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        
        binding.movieDetailFavorite.setOnClickListener{
            viewModel.onFavoriteClicked()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { it.movie?.let { it1 -> binding.updateUI(it1) } }
            }
        }
    }
    
    private fun FragmentDetailBinding.updateUI(movie: com.jorgesm.themoviedb.domain.DomainMovie) {
        val background = movie.backdropPath ?: movie.posterPath
        movieDetailToolbar.title = movie.title
        detailImage.loadUrl(Constants.IMG_BASE_URL+Constants.IMG_780+background)
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovieDetailText(movie)
        val favoriteIcon = if (movie.isFavorite)R.drawable.baseline_favorite_32_red
        else R.drawable.baseline_favorite_32
        movieDetailFavorite.setImageResource(favoriteIcon)
    }
}