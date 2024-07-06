package com.movie.gallery.repository

import com.movie.gallery.api.MovieApi
import com.movie.gallery.room.MovieDao
import com.movie.gallery.room.MovieEntity
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) {

    suspend fun getMovies(
        pageNumber: Int
    ) = movieApi.getMovies(false, false, "en-US", pageNumber, "popularity.desc")

    suspend fun getMovieDetails(movieId: Int) = movieApi.getMovieDetails(movieId, "en-US")

    suspend fun insertFavouriteMovie(movieEntity: MovieEntity) = movieDao.insertFavouriteMovie(movieEntity)

    fun getFavouriteMovie() = movieDao.getFavouriteMovies()

    suspend fun deleteFavouriteMovie(movieEntity: MovieEntity) = movieDao.removeFavouriteMovie(movieEntity)
}