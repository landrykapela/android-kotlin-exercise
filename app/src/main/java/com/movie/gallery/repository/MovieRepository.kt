package com.movie.gallery.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.movie.gallery.api.MovieApi
import com.movie.gallery.model.Result
import com.movie.gallery.room.MovieDao
import com.movie.gallery.room.MovieEntity
import com.movie.gallery.util.ResponseResource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    @ApplicationContext private val context:Context
) {

    private val _movieListResponseResource =
        MutableStateFlow<ResponseResource<List<Result>>>(ResponseResource.Loading())
    val movieListResponseResource: StateFlow<ResponseResource<List<Result>>> =
        _movieListResponseResource

    fun getMovies(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi,_movieListResponseResource,context) }
        ).flow
    }

    suspend fun getMovieDetails(movieId: Int) = movieApi.getMovieDetails(movieId, "en-US")

    suspend fun insertFavouriteMovie(movieEntity: MovieEntity) =
        movieDao.insertFavouriteMovie(movieEntity)

    fun getFavouriteMovie() = movieDao.getFavouriteMovies()

    suspend fun deleteFavouriteMovie(movieEntity: MovieEntity) =
        movieDao.removeFavouriteMovie(movieEntity)
}