package com.movie.gallery.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movie.gallery.R
import com.movie.gallery.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    private lateinit var _binding:FragmentFavouriteBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        return _binding.root
    }

}