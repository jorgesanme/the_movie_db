package com.jorgesm.themoviedb.ui.filter_by_genres


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentMainBinding
import com.jorgesm.themoviedb.ui.main.MainState
import com.jorgesm.themoviedb.ui.main.MoviesAdapter
import com.jorgesm.themoviedb.ui.main.buildMainState
import com.jorgesm.themoviedb.utils.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : Fragment(R.layout.fragment_main) {
    
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var mainState: MainState
    
    private val viewModel: GenderViewModel by viewModels()
    private val adapter = MoviesAdapter{ mainState.onMovieClicked(it) }
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        mainBinding = FragmentMainBinding.bind(view).apply {
            rvCovers.adapter = adapter
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
        setupToolbar()
        setupObservable()
    }
    private fun setupToolbar(){
//        activity?.actionBar?.title = getString(R.string.filter_by_genres_title)
        //        activity?.actionBar?.title =
        mainBinding.movieDetailToolbar.title = getString(R.string.filter_by_genres_title)
    }
    
    private fun setupObservable() {
        viewLifecycleOwner.launchAndCollect(viewModel.state){
            mainBinding.progressCircular.visibility = if(it.loading) View.VISIBLE else View.GONE
            adapter.submitList(it.movies)
            
            mainBinding.errorText.apply {
                visibility = if (it.error != null) View.VISIBLE else View.GONE
                text = it.error?.let(mainState::errorToString)
            }
        }
     }
}