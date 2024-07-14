package com.movie.gallery.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movie.gallery.R
import com.movie.gallery.model.MovieDetail
import com.movie.gallery.model.Result
import com.movie.gallery.repository.MovieRepository
import com.movie.gallery.room.MovieEntity
import com.movie.gallery.util.ResponseResource
import com.movie.gallery.util.UtilityClass
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val uiState: StateFlow<ResponseResource<List<Result>>> =
        movieRepository.movieListResponseResource

    val popularMovies: LiveData<PagingData<Result>> =
        movieRepository.getMovies().cachedIn(viewModelScope).asLiveData()


    private val _movieDetailResponseResource: MutableLiveData<ResponseResource<MovieDetail>> =
        MutableLiveData()

    val movieDetailResponseResource: MutableLiveData<ResponseResource<MovieDetail>>
        get() = _movieDetailResponseResource


    //Method to retrieve movie details from api
    fun getMovieDetails(@ApplicationContext context: Context, movieId: Int) {
        _movieDetailResponseResource.value = ResponseResource.Loading()
        if (UtilityClass.isConnectedToInternet(context)) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val response = movieRepository.getMovieDetails(movieId)

                        withContext(Dispatchers.Main) {

                            _movieDetailResponseResource.value =
                                UtilityClass.getResponseData(context, response)
                        }


                    } catch (throwable: Throwable) {
                        withContext(Dispatchers.Main) {
                            _movieDetailResponseResource.value =
                                UtilityClass.getThrowableError(context, throwable)
                        }
                    }
                }
            }
        } else {
            _movieDetailResponseResource.value =
                ResponseResource.Error(context.getString(R.string.no_internet_connection))
        }
    }

    fun insertFavouriteMovie(movieEntity: MovieEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    movieRepository.insertFavouriteMovie(movieEntity)
                } catch (_: Exception) {

                }
            }
        }
    }

    fun getAllFavouriteMovies(): LiveData<MutableList<MovieEntity>> {
        return movieRepository.getFavouriteMovie()
    }

    fun deleteFavouriteMovie(movieEntity: MovieEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieRepository.deleteFavouriteMovie(movieEntity)
            }
        }
    }


}