package com.movie.gallery.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.movie.gallery.R
import com.movie.gallery.model.Result
import com.movie.gallery.room.MovieEntity
import com.movie.gallery.util.UtilityClass
import java.io.ByteArrayOutputStream

class MoviesAdapter(private val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var movieList: MutableList<Result> = mutableListOf()

    var onFavouriteImageClicked: ((MovieEntity) -> Unit)? = null
    val isFavouriteButtonClicked = false

    fun setData(data: MutableList<Result>) {
        movieList = data
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_movies, parent, false)
        return MovieViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        val imageView = holder.itemView.findViewById<ImageView>(R.id.posterImage)

        //load image using glide

        Glide.with(context)
            .load(UtilityClass.TMDB_IMAGE_BASE_URL + movie.posterPath)
            .into(imageView).also {
                holder.itemView.findViewById<ImageView>(R.id.imgFavourite).apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        this.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite_filled))
                        loadImageAndConvertToBase64(UtilityClass.TMDB_IMAGE_BASE_URL + movie.posterPath) { base64 ->
                            val movieEntity = MovieEntity(
                                movie.id,
                                movie.title,
                                movie.overview,
                                base64,
                                movie.releaseDate
                            )

                            onFavouriteImageClicked?.invoke(movieEntity)

                        }

                    }
                }
            }

        holder.itemView.findViewById<TextView>(R.id.txtTitle).text = movie.title

        holder.itemView.findViewById<RatingBar>(R.id.ratingBar).rating =
            (movie.voteAverage / 2).toFloat()
        holder.itemView.findViewById<TextView>(R.id.txtRating).text =
            "%.1f".format(movie.voteAverage) + "/10"


    }

    override fun getItemCount(): Int {
        return if (movieList.isNotEmpty()) {
            movieList.size
        } else {
            0
        }
    }


    private fun loadImageAndConvertToBase64(imageUrl: String, onComplete: (String) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val base64String = bitmapToBase64(resource)
                    onComplete(base64String)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}