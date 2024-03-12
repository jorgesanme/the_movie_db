package com.jorgesm.themoviedb.ui.main



import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentMainBinding
import com.jorgesm.themoviedb.data.MoviesRepository
import com.jorgesm.themoviedb.usecases.GetPopularMoviesUseCase
import com.jorgesm.themoviedb.usecases.RequestPopularMoviesUseCase
import com.jorgesm.themoviedb.utils.app
import com.jorgesm.themoviedb.utils.launchAndCollect

class MainFragment : Fragment(R.layout.fragment_main) {
    
    private val viewModel: MainViewModel by viewModels {
        val repository = MoviesRepository(requireActivity().app)
        MainViewModelFactory(
            GetPopularMoviesUseCase(repository),
            RequestPopularMoviesUseCase(repository)
        )
    }
    private val adapter = MoviesAdapter{ mainState.onMovieClicked(it) }
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var mainState: MainState
    
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