package com.jorgesm.themoviedb.ui.filter_by_genres


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentGenresBinding
import com.jorgesm.themoviedb.domain.DomainGenre
import com.jorgesm.themoviedb.ui.main.MainState
import com.jorgesm.themoviedb.ui.main.MoviesAdapter
import com.jorgesm.themoviedb.ui.main.buildMainState
import com.jorgesm.themoviedb.utils.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : Fragment(R.layout.fragment_genres) {

    private lateinit var mainBinding: FragmentGenresBinding
    private lateinit var mainState: MainState
    private lateinit var context: Context

    private val viewModel: GenderViewModel by viewModels()
    private val adapter = MoviesAdapter { mainState.onMovieClickedFromFilterByGenres(it.id) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        mainBinding = FragmentGenresBinding.bind(view).apply {
            rvCovers.adapter = adapter
        }
        context = mainBinding.root.context
        mainState.requestLocationPermission { viewModel.onUiReady() }
        setupToolbar()
        setupObservable()
    }

    private fun setupToolbar() {
        mainBinding.movieDetailToolbar.title = getString(R.string.filter_by_genres_title)
    }


    private fun setupObservable() {
        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            mainBinding.progressCircular.visibility = if (it.loading) View.VISIBLE else View.GONE
            adapter.submitList(it.movies)
            mainBinding.errorText.apply {
                visibility = if (it.error != null) View.VISIBLE else View.GONE
                text = it.error?.let(mainState::errorToString)
            }
        }
        viewLifecycleOwner.launchAndCollect(viewModel.genres) {
            setupDropdown(it)
        }
    }

    private fun setupDropdown(genres: List<DomainGenre>) {
        val spinner = mainBinding.movieFilterSpinner
        val options = genres.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = options[position]
                val genreId = genres.firstOrNull { it.name == selected }?.id
                genreId?.let { genre ->
                    viewModel.getListByGenre(genre.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}