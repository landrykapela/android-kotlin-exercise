package com.movie.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.movie.gallery.R

class MoviesAdapter:RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movieList = listOf(
        R.drawable.sampler_image, R.drawable.sampler_image, R.drawable.sampler_image,
        R.drawable.sampler_image, R.drawable.sampler_image, R.drawable.sampler_image,
        R.drawable.sampler_image, R.drawable.sampler_image, R.drawable.sampler_image,
        R.drawable.sampler_image, R.drawable.sampler_image, R.drawable.sampler_image,
        R.drawable.sampler_image, R.drawable.sampler_image, R.drawable.sampler_image,
        R.drawable.sampler_image, R.drawable.sampler_image, R.drawable.sampler_image
    )

    class MovieViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.layout_movies,parent,false)
        return MovieViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=movieList[position]
        val imageView=holder.itemView.findViewById<ImageView>(R.id.posterImage)
        imageView.setImageResource(movie)
    }

    override fun getItemCount(): Int {
        return if (movieList.isNotEmpty()){
            movieList.size
        }else{
            0
        }
    }
}