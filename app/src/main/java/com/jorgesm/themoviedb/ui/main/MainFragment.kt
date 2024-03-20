package com.jorgesm.themoviedb.ui.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentMainBinding
import com.jorgesm.themoviedb.utils.app
import com.jorgesm.themoviedb.utils.launchAndCollect
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    
    @Inject lateinit var viewModelFactory: MainViewModelFactory
    
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var mainState: MainState
    
    private val viewModel: MainViewModel by viewModels { viewModelFactory}
    private val adapter = MoviesAdapter{ mainState.onMovieClicked(it) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.component.inject(this)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        mainBinding = FragmentMainBinding.bind(view).apply {
            rvCovers.adapter = adapter
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
        setupObservable()
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