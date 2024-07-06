package com.movie.gallery.api

import com.movie.gallery.model.MovieDetail
import com.movie.gallery.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId:Int,@Query("language") language:String):Response<MovieDetail>

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("include_adult") includeAdult:Boolean,
        @Query("include_video") includeVideo:Boolean,
        @Query("language") language: String,
        @Query("page") pageNumber:Int,
        @Query("sort_by") sortedBy:String
    ):Response<MovieList>

}