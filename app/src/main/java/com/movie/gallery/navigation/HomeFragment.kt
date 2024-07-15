package com.movie.gallery.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.movie.gallery.adapter.MoviesAdapter
import com.movie.gallery.databinding.FragmentHomeBinding
import com.movie.gallery.util.ResponseResource
import com.movie.gallery.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesAdapter = MoviesAdapter(requireActivity())

        binding.movieRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }

        binding.movieRecyclerView.adapter = moviesAdapter

        movieViewModel.popularMovies.observe(requireActivity()) {
            moviesAdapter.submitData(lifecycle, it)
        }

        movieViewModel.uiState.onEach {
            when (it) {
                is ResponseResource.Loading -> {
                    binding.pBarMovies.visibility = View.VISIBLE

                }

                is ResponseResource.Success -> {
                    binding.pBarMovies.visibility = View.INVISIBLE

                }

                is ResponseResource.Error -> {
                    binding.pBarMovies.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(lifecycleScope)


        binding.swipeRefreshLayout.setOnRefreshListener {
            moviesAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        moviesAdapter.onFavouriteImageClicked = {
            movieViewModel.insertFavouriteMovie(it)
            Toast.makeText(
                requireActivity(),
                it.movieTitle + " Saved as favourite",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}