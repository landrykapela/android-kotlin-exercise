package com.movie.gallery.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query


@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavouriteMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_entity ORDER BY movie_id DESC")
    fun getFavouriteMovies():LiveData<MutableList<MovieEntity>>


    @Delete
    suspend fun removeFavouriteMovie(movieEntity: MovieEntity)
}