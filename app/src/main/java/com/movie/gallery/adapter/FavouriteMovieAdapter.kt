package com.movie.gallery.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movie.gallery.R
import com.movie.gallery.room.MovieEntity

class FavouriteMovieAdapter(private val context: Context):RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieHolder>() {

    private var favouriteMovies:MutableList<MovieEntity> = mutableListOf()

    fun setData(favouriteMoviesData:MutableList<MovieEntity>){
        favouriteMovies=favouriteMoviesData
    }
    class FavouriteMovieHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_favourite_movies,parent,false)
        return FavouriteMovieHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteMovieHolder, position: Int) {
        val movieEntity=favouriteMovies[position]
        val bitmapImage=base64ToBitmap(movieEntity.posterPath)
        holder.itemView.findViewById<ImageView>(R.id.imgFavourite).setImageBitmap(bitmapImage)
        holder.itemView.findViewById<TextView>(R.id.txtMovieTitle).text=movieEntity.movieTitle
        holder.itemView.findViewById<TextView>(R.id.txtRelease).text=movieEntity.releaseDate
        holder.itemView.findViewById<TextView>(R.id.txtOverview).text=movieEntity.overview
    }

    override fun getItemCount(): Int {
        return if(favouriteMovies.size>0){
            favouriteMovies.size
        }else{
            0
        }
    }

    private fun base64ToBitmap(base64String: String): Bitmap {
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}