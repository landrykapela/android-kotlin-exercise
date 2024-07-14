package com.movie.gallery.repository

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movie.gallery.api.MovieApi
import com.movie.gallery.model.Result
import com.movie.gallery.util.ResponseResource
import com.movie.gallery.util.UtilityClass
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieApi: MovieApi,
    private val _responseResource: MutableStateFlow<ResponseResource<List<Result>>>,
    private val context: Context
) :
    PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        val page = params.key ?: 1
        if (page == 1) {
            _responseResource.value = ResponseResource.Loading()
        }
        if (UtilityClass.isConnectedToInternet(context)) {
            return try {
                val response = movieApi.getMovies(
                    includeAdult = false,
                    includeVideo = false,
                    language = "en-US",
                    page,
                    sortedBy = "popularity.desc"
                )

                if (response.isSuccessful && response.body() != null) {
                    val movies = response.body()!!.results
                    _responseResource.value = ResponseResource.Success(movies)
                    LoadResult.Page(
                        data = movies,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (movies.isEmpty()) null else page + 1
                    )
                } else {
                    _responseResource.value =
                        ResponseResource.Error("There is an error ${response.code()}")
                    LoadResult.Error(HttpException(response))
                }

            } catch (e: IOException) {
                _responseResource.value = ResponseResource.Error("There is an error $e")
                return LoadResult.Error(e)
            } catch (e: HttpException) {
                _responseResource.value = ResponseResource.Error("There is an error $e")
                return LoadResult.Error(e)
            }
        } else {
            _responseResource.value = ResponseResource.Error("No Internet Connection")
            return LoadResult.Error(IOException("No Internet Connection"))

        }
    }
}

