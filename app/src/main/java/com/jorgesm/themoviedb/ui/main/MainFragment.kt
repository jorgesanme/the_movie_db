package com.jorgesm.themoviedb.ui.main



import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentMainBinding
import com.jorgesm.themoviedb.model.Movie
import com.jorgesm.themoviedb.model.MoviesRepository
import com.jorgesm.themoviedb.utils.launchAndCollect
import com.jorgesm.themoviedb.utils.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {
    
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity() as AppCompatActivity)) }
    private val adapter = MoviesAdapter{ viewModel.onMovieClicked(it) }
    private lateinit var mainBinding: FragmentMainBinding
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding = FragmentMainBinding.bind(view).apply {
            rvCovers.adapter = adapter
        }
        setupObservable()
    }
    
    private fun setupObservable() {
        viewLifecycleOwner.launchAndCollect(viewModel.state){mainBinding.updateUI(it)}
        
        
        /*viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { mainBinding.updateUI(it) }
            }
        }*/
     }
    
    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progressCircular.visible = state.loading
        state.movies.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }
    
    private fun navigateTo(movie: Movie){
        val navAction = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(navAction)
        viewModel.onNavigationDone()
    }
}
