package com.jorgesm.themoviedb.ui.filter_by_name


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.databinding.FragmentByNameBinding
import com.jorgesm.themoviedb.ui.main.MainState
import com.jorgesm.themoviedb.ui.main.MoviesAdapter
import com.jorgesm.themoviedb.ui.main.buildMainState
import com.jorgesm.themoviedb.utils.dpToPx
import com.jorgesm.themoviedb.utils.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterByNameFragment : Fragment(R.layout.fragment_by_name) {
    
    private lateinit var mainBinding: FragmentByNameBinding
    private lateinit var mainState: MainState
    
    private val viewModel: FilterByNameViewModel by viewModels()
    private val adapter = MoviesAdapter{ mainState.onMovieClickedFromFilterByName(it.id) }
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        mainBinding = FragmentByNameBinding.bind(view).apply {
            rvCovers.adapter = adapter
        }
        setupObservable()
        setupToolbar()
        setupView()
    }
    private fun setupToolbar(){
        mainBinding.movieDetailToolbar.title = getString(R.string.filter_by_name_title)
    }

    private fun setupView(){
        mainBinding.fabByName.setOnClickListener{
            showNameInputDialog(requireContext()){movieName ->
                viewModel.getMovieByName(movieName)
            }
        }
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

    private fun showNameInputDialog(context: Context, onNameEntered: (String) -> Unit) {
        val input = EditText(context).apply {
            hint = context.getString(R.string.input_text_filter_by_movie_hint)
            inputType = InputType.TYPE_CLASS_TEXT
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                this.marginStart = 16.dpToPx()
            }
        }

        AlertDialog.Builder(context)
            .setTitle(getString(R.string.dialog_search_movie_by_title))
            .setView(input)
            .setPositiveButton(getString(R.string.button_search)) { dialog, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    onNameEntered(name)
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}