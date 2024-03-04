package com.jorgesm.themoviedb.ui.main



import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentMainBinding
import com.jorgesm.themoviedb.model.MoviesRepository
import com.jorgesm.themoviedb.utils.app
import com.jorgesm.themoviedb.utils.launchAndCollect
import com.jorgesm.themoviedb.utils.visible
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainFragment : Fragment(R.layout.fragment_main) {
    
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().app)) }
    private val adapter = MoviesAdapter{ mainState.onMovieClicked(it) }
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var mainState: MainState
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        mainBinding = FragmentMainBinding.bind(view).apply {
            rvCovers.adapter = adapter
        }
        setupObservable()
    }
    
    private fun setupObservable() {
        with(viewModel.state){
            diff({it.movies}, adapter::submitList)
            diff({it.loading}){mainBinding.progressCircular.visible = it}
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
     }
    
    
    private fun <T,U> Flow<T>.diff(mapf: (T)-> U, body:(U)-> Unit){
        viewLifecycleOwner.launchAndCollect(
            flow = this.map(mapf).distinctUntilChanged(),
            body = body
        )
    }
}
