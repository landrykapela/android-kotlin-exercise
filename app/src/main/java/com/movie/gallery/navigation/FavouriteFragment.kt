package com.movie.gallery.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie.gallery.R
import com.movie.gallery.adapter.FavouriteMovieAdapter
import com.movie.gallery.databinding.FragmentFavouriteBinding
import com.movie.gallery.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var _binding:FragmentFavouriteBinding
    private val binding get() = _binding

    private val movieViewModel:MovieViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favouriteMovieAdapter=FavouriteMovieAdapter(requireActivity())

        binding.pBarFavouriteMovies.visibility=View.VISIBLE

        binding.rvFavouriteMovie.layoutManager=LinearLayoutManager(requireActivity())

        movieViewModel.getAllFavouriteMovies().observe(requireActivity()){
            favouriteMovieAdapter.setData(it)
            binding.rvFavouriteMovie.adapter=favouriteMovieAdapter
            favouriteMovieAdapter.notifyDataSetChanged()
            binding.pBarFavouriteMovies.visibility=View.INVISIBLE
        }

    }

}