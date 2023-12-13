package com.jorgesm.themoviedb.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.model.Movie
import com.jorgesm.themoviedb.databinding.FragmentDetailBinding
import com.jorgesm.themoviedb.utils.Constants
import com.jorgesm.themoviedb.utils.loadUrl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment: Fragment(R.layout.fragment_detail) {
    
    private val safeArgs: DetailFragmentArgs by navArgs()
    
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.DetailViewModelFactory(requireNotNull( safeArgs.movie))
    }
    
    private lateinit var binding: FragmentDetailBinding
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        binding.movieDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { binding.updateUI(it.movie) }
            }
        }
    }
    
    private fun FragmentDetailBinding.updateUI(movie: Movie) {
        val background = movie.backdropPath ?: movie.posterPath
        movieDetailToolbar.title = movie.title
        detailImage.loadUrl(Constants.IMG_BASE_URL+Constants.IMG_780+background)
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovieDetailText(movie)
    }
}